package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.config.StravaConfigProperties;
import com.jobdk.stravametricsapi.model.Activity;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActivityRetrievalService {
  private static final Logger LOG = LoggerFactory.getLogger(ActivityRetrievalService.class);
  private final RestTemplate restTemplate;
  private final StravaConfigProperties stravaConfigProperties;
  private List<Activity> activities = new ArrayList<>();

  public ActivityRetrievalService(
      RestTemplate restTemplate, StravaConfigProperties stravaConfigProperties) {
    this.restTemplate = restTemplate;
    this.stravaConfigProperties = stravaConfigProperties;
  }

  public List<Activity> getAllActivities() {
    return getActivities(null);
  }

  public List<Activity> getActivitiesAfterLastRetrieval(
      String dateOfLastRetrieval, long elapsedTime) {
    return getActivities(getEpochTime(dateOfLastRetrieval, elapsedTime));
  }

  private Long getEpochTime(String dateOfLastRetrieval, long elapsedTime) {
    if (dateOfLastRetrieval != null) {
      try {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateOfLastRetrieval).getTime()
                / 1000
            + elapsedTime;
      } catch (Exception e) {
        LOG.error(
            MessageFormat.format("Could not parse dateOfLastRetrieval: {0}", dateOfLastRetrieval));
      }
    }
    return null;
  }

  private List<Activity> getActivities(Long dateOfLastRetrieval) {
    int page = 1;
    while (true) {
      List<Activity> result = getLastFiftyActivities(page, dateOfLastRetrieval);
      activities.addAll(result);
      if (result == null || result.size() < 50) {
        break;
      }
      page++;
    }
    return activities;
  }

  private List<Activity> getLastFiftyActivities(int page, Long dateOfLastRetrieval) {
    String url = buildUrl(page);
    if (dateOfLastRetrieval != null) url += "&after=" + dateOfLastRetrieval;

    ResponseEntity<List<Activity>> exchange =
        restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Activity>>() {});
    return exchange.getBody();
  }

  private String buildUrl(int page) {
    return String.format(
        "%sactivities?access_token=%s&per_page=50&page=%d",
        stravaConfigProperties.apiUrl(), stravaConfigProperties.accessToken(), page);
  }
}
