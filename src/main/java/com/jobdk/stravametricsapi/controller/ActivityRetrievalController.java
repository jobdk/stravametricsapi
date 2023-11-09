package com.jobdk.stravametricsapi.controller;

import com.jobdk.stravametricsapi.model.activity.Activity;
import com.jobdk.stravametricsapi.service.ActivityRetrievalService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityRetrievalController {

  private final ActivityRetrievalService activityRetrievalService;

  public ActivityRetrievalController(ActivityRetrievalService activityRetrievalService) {
    this.activityRetrievalService = activityRetrievalService;
  }

  @GetMapping("strava")
  public List<Activity> getAllActivitiesFromStrava() {
    return activityRetrievalService.getAllActivitiesFromStrava();
  }

  @GetMapping
  public List<Activity> getAllActivitiesFromDatabase() {
    return activityRetrievalService.getAllActivitiesFromDatabase();
  }

  @GetMapping("strava/update")
  public List<Activity> getActivitiesNewestActivitiesFromStrava() {
    return activityRetrievalService.updateActivities();
  }
}
