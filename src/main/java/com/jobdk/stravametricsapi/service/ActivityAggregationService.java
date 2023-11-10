package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.model.aggregation.KilometersPerMonth;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerWeek;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerYear;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class ActivityAggregationService {
  private static final String ACTIVITY = "activity";
  private static final String DISTANCE = "distance";
  private static final String START_DATA = "startDate";
  private static final String TYPE = "type";
  private static final String MONTH = "month";
  private static final String TOTAL_AMOUNT = "totalAmount";
  private final MongoTemplate mongoTemplate;

  public ActivityAggregationService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<KilometersPerMonth> getKilometerPerMonth(String type) {
    Aggregation aggregation = getAggregationPerMonth(type);
    return mongoTemplate
        .aggregate(aggregation, ACTIVITY, KilometersPerMonth.class)
        .getMappedResults();
  }

  private Aggregation getAggregationPerMonth(String type) {
    return Aggregation.newAggregation(
        Aggregation.match(Criteria.where(TYPE).is(type)),
        Aggregation.project()
            .andExpression("{$month: '$" + START_DATA + "'}")
            .as(MONTH)
            .andExpression("{$year: '$" + START_DATA + "'}")
            .as("year")
            .and(DISTANCE)
            .as(DISTANCE),
        Aggregation.group("year", MONTH).sum(DISTANCE).as(TOTAL_AMOUNT),
        Aggregation.project()
            .andExpression("_id.year")
            .as("year")
            .andExpression("_id.month")
            .as(MONTH)
            .and(TOTAL_AMOUNT)
            .divide(1000)
            .as(TOTAL_AMOUNT),
        Aggregation.sort(Sort.Direction.DESC, MONTH, "year"));
  }

  public List<KilometersPerWeek> getKilometerPerWeek(String type) {
    Aggregation aggregation = getAggregationPerWeek(type);
    return mongoTemplate
        .aggregate(aggregation, ACTIVITY, KilometersPerWeek.class)
        .getMappedResults();
  }

  private Aggregation getAggregationPerWeek(String type) {
    return Aggregation.newAggregation(
        Aggregation.match(Criteria.where(TYPE).is(type)),
        Aggregation.project()
            .andExpression("{$week: '$" + START_DATA + "'}")
            .as("week")
            .andExpression("{$year: '$" + START_DATA + "'}")
            .as("year")
            .and(DISTANCE)
            .divide(1000)
            .as(DISTANCE),
        Aggregation.group("week", "year").sum(DISTANCE).as(TOTAL_AMOUNT),
        Aggregation.project()
            .andExpression("_id.year")
            .as("year")
            .andExpression("_id.month")
            .as(MONTH)
            .andExpression("_id.week")
            .as("week")
            .and(TOTAL_AMOUNT)
            .as(TOTAL_AMOUNT),
        Aggregation.sort(Sort.Direction.DESC, "year", "week"));
  }

  public List<KilometersPerYear> getKilometerPerYear(String type) {
    Aggregation aggregation = getAggregationPerYear(type);
    return mongoTemplate
        .aggregate(aggregation, ACTIVITY, KilometersPerYear.class)
        .getMappedResults();
  }

  private Aggregation getAggregationPerYear(String type) {
    return Aggregation.newAggregation(
        Aggregation.match(Criteria.where(TYPE).is(type)),
        Aggregation.project()
            .andExpression("{$year: '$" + START_DATA + "'}")
            .as("year")
            .and(DISTANCE)
            .divide(1000)
            .as(DISTANCE),
        Aggregation.group("year").sum(DISTANCE).as(TOTAL_AMOUNT),
        Aggregation.sort(Sort.Direction.ASC, "_id"));
  }
}
