package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.exception.NoActivitiesFoundException;
import com.jobdk.stravametricsapi.model.activity.Activity;
import com.jobdk.stravametricsapi.repository.ActivityRepository;
import java.text.MessageFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ActivityRetrievalService {
  private static final Logger LOG = LoggerFactory.getLogger(ActivityRetrievalService.class);

  private final RestTemplate restTemplate;
  private final StravaConfigProperties stravaConfigProperties;
  private final AuthTokenRefresherService authTokenRefresherService;
  private final ActivityRepository activityRepository;

  public ActivityRetrievalService(
      RestTemplate restTemplate,
      StravaConfigProperties stravaConfigProperties,
      AuthTokenRefresherService authTokenRefresherService,
      ActivityRepository activityRepository) {
    this.restTemplate = restTemplate;
    this.stravaConfigProperties = stravaConfigProperties;
    this.authTokenRefresherService = authTokenRefresherService;
    this.activityRepository = activityRepository;
  }

  private static NoActivitiesFoundException getNoActivitiesFoundException() {
    return new NoActivitiesFoundException("No activities in database");
  }

  private static Optional<Long> createEpochTime(Date dateOfLastRetrieval, long elapsedTime) {
    return Optional.of(dateOfLastRetrieval.getTime() / 1000 + elapsedTime);
  }

  /** Gets all activities and stores them in mongo db */
  public List<Activity> getAllActivitiesFromStrava() {
    return getActivities(Optional.empty());
  }

  public List<Activity> getAllActivitiesFromDatabase() {
    return retrieveAllActivitiesFromDatabase();
  }

  /** Gets the newest activities and stores them to mongodb */
  public List<Activity> updateActivities() {
    Optional<Activity> firstActivity =
        activityRepository
            .findActivitiesByOrderByStartDateDesc()
            .flatMap(activities -> activities.stream().findFirst());

    firstActivity.ifPresentOrElse(
        activity -> getActivities(getEpochTime(activity.getStartDate(), activity.getElapsedTime())),
        () -> {
          throw getNoActivitiesFoundException();
        });

    return retrieveAllActivitiesFromDatabase();
  }

  private List<Activity> retrieveAllActivitiesFromDatabase() {
    return activityRepository
        .findActivitiesByOrderByStartDateDesc()
        .orElseThrow(ActivityRetrievalService::getNoActivitiesFoundException);
  }

  private Optional<Long> getEpochTime(Date dateOfLastRetrieval, long elapsedTime) {
    if (dateOfLastRetrieval != null) {
      try {
        return createEpochTime(dateOfLastRetrieval, elapsedTime);
      } catch (Exception e) {
        LOG.error(
            MessageFormat.format("Could not parse dateOfLastRetrieval: {0}", dateOfLastRetrieval));
      }
    }
    return Optional.empty();
  }

  private List<Activity> getActivities(Optional<Long> dateOfLastRetrieval) { // TODO: parallelize
    int page = 1;
    while (true) {
      Optional<List<Activity>> result = getLastFiftyActivities(page, dateOfLastRetrieval);
      result.ifPresent(activityRepository::saveAll);
      if (result.isEmpty() || result.get().size() < 50) break;
      page++;
    }
    return retrieveAllActivitiesFromDatabase();
  }

  private Optional<List<Activity>> getLastFiftyActivities(
      int page, Optional<Long> dateOfLastRetrieval) {
    try {
      return Optional.ofNullable(retrieveActivities(page, dateOfLastRetrieval));
    } catch (HttpClientErrorException.Unauthorized e) {
      LOG.error("Could not retrieve activities from Strava API", e);
      authTokenRefresherService.refreshToken();
      return Optional.ofNullable(retrieveActivities(page, dateOfLastRetrieval));
    } catch (Exception e) {
      LOG.error("Could not retrieve activities from Strava API", e);
      return Optional.empty();
    }
  }

  private List<Activity> retrieveActivities(int page, Optional<Long> dateOfLastRetrieval) {
    String url = buildUrl(page, dateOfLastRetrieval);
    return restTemplate
        .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Activity>>() {})
        .getBody();
  }

  private String buildUrl(int page, Optional<Long> dateOfLastRetrieval) {
    return UriComponentsBuilder.newInstance()
        .scheme(stravaConfigProperties.getProtocol())
        .host(stravaConfigProperties.getHost())
        .path(stravaConfigProperties.getApiPath())
        .path("/activities")
        .queryParam("access_token", stravaConfigProperties.getAccessToken())
        .queryParam("per_page", 50)
        .queryParam("page", page)
        .queryParamIfPresent("after", dateOfLastRetrieval)
        .toUriString();
  }
}
