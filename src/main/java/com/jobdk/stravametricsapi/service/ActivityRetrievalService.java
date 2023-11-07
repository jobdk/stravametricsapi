package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.model.activity.Activity;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  private List<Activity> activities =
      new ArrayList<>(); // TODO: could be added to json file without loading it all or mongo db

  public ActivityRetrievalService(
      RestTemplate restTemplate,
      StravaConfigProperties stravaConfigProperties,
      AuthTokenRefresherService authTokenRefresherService) {
    this.restTemplate = restTemplate;
    this.stravaConfigProperties = stravaConfigProperties;
    this.authTokenRefresherService = authTokenRefresherService;
  }

  public List<Activity> getAllActivities() {
    return getActivities(Optional.empty());
  }

  public List<Activity> getActivitiesAfterLastRetrieval(
      String dateOfLastRetrieval, long elapsedTime) {
    return getActivities(getEpochTime(dateOfLastRetrieval, elapsedTime));
  }

  private Optional<Long> getEpochTime(String dateOfLastRetrieval, long elapsedTime) {
    if (dateOfLastRetrieval != null) {
      try {
        return createEpochTime(dateOfLastRetrieval, elapsedTime);
      } catch (Exception e) {
        LOG.error(
            MessageFormat.format(
                "Could not parse dateOfLastRetrieval: {0}",
                dateOfLastRetrieval)); // TODO: Exception handling
      }
    }
    return Optional.empty();
  }

  private static Optional<Long> createEpochTime(String dateOfLastRetrieval, long elapsedTime)
      throws ParseException {
    return Optional.of(
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateOfLastRetrieval).getTime() / 1000
            + elapsedTime);
  }

  private List<Activity> getActivities(Optional<Long> dateOfLastRetrieval) {
    int page = 1;
    while (true) {
      List<Activity> result = getLastFiftyActivities(page, dateOfLastRetrieval);
      activities.addAll(result);
      if (result.size() < 50) {
        break;
      }
      page++;
    }
    return activities;
  }

  private List<Activity> getLastFiftyActivities(int page, Optional<Long> dateOfLastRetrieval) {

    try {
      return retrieveActivities(page, dateOfLastRetrieval);
    } catch (HttpClientErrorException.Unauthorized e) {
      LOG.error("Could not retrieve activities from Strava API", e);
      authTokenRefresherService.refreshToken();
      return retrieveActivities(page, dateOfLastRetrieval);
    } catch (Exception e) {
      LOG.error("Could not retrieve activities from Strava API", e);
      return List.of();
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
