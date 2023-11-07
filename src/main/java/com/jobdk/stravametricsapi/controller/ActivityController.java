package com.jobdk.stravametricsapi.controller;

import com.jobdk.stravametricsapi.model.Activity;
import com.jobdk.stravametricsapi.service.ActivityRetrievalService;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class ActivityController {

  private final ActivityRetrievalService activityRetrievalService;

  public ActivityController(ActivityRetrievalService activityRetrievalService) {
    this.activityRetrievalService = activityRetrievalService;
  }

  // Is called on setup
  @RequestMapping("/activities")
  public List<Activity> getActivities() {
    return activityRetrievalService.getAllActivities();
  }

  @RequestMapping("/activities/{date}/{elapsed_time}")
  public List<Activity> getActivitiesAfterLastRetrieval(
      @PathVariable("date") String date, @PathVariable("elapsed_time") long elapsedTime) {
    return activityRetrievalService.getActivitiesAfterLastRetrieval(date, elapsedTime);
  }
}
