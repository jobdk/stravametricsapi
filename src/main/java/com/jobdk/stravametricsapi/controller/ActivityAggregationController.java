package com.jobdk.stravametricsapi.controller;

import com.jobdk.stravametricsapi.model.aggregation.KilometersPerMonth;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerWeek;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerYear;
import com.jobdk.stravametricsapi.service.ActivityAggregationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activities/aggregation")
public class ActivityAggregationController {
  private final ActivityAggregationService activityAggregationService;

  public ActivityAggregationController(ActivityAggregationService activityAggregationService) {
    this.activityAggregationService = activityAggregationService;
  }

  @GetMapping("kilometer/week/{type}")
  public List<KilometersPerWeek> getRunningKilometerPerWeek(@PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerWeek(type);
  }

  @GetMapping("kilometer/month/{type}")
  public List<KilometersPerMonth> getRunningKilometerPerMonth(@PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerMonth(type);
  }

  @GetMapping("kilometer/year/{type}")
  public List<KilometersPerYear> getRunningKilometerPerYear(@PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerYear(type);
  }
}
