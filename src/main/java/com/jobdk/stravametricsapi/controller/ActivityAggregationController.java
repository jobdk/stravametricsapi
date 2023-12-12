package com.jobdk.stravametricsapi.controller;

import com.jobdk.stravametricsapi.model.aggregation.KilometersPerMonth;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerWeek;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerYear;
import com.jobdk.stravametricsapi.service.ActivityAggregationService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activities/aggregation")
public class ActivityAggregationController {
  private final ActivityAggregationService activityAggregationService;

  public ActivityAggregationController(ActivityAggregationService activityAggregationService) {
    this.activityAggregationService = activityAggregationService;
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("kilometer/week/{type}")
  public List<KilometersPerWeek> getRunningKilometerPerWeek(@PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerWeek(type);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("kilometer/month/{type}")
  public List<KilometersPerMonth> getRunningKilometerPerMonth(@PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerMonth(type);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("kilometer/year/{type}")
  public List<KilometersPerYear> getRunningKilometerPerYear(@PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerYear(type);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("kilometer/week/goals/{type}")
  public List<KilometersPerWeek> getRunningKilometerPerWeekGoals(
      @PathVariable("type") String type) {
    return activityAggregationService.getKilometerPerWeekGoals(type);
  }
}
