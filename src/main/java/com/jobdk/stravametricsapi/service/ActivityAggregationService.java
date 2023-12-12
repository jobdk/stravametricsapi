package com.jobdk.stravametricsapi.service;

import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;
import static org.springframework.util.ResourceUtils.getFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerMonth;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerWeek;
import com.jobdk.stravametricsapi.model.aggregation.KilometersPerYear;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class ActivityAggregationService {
  private final Logger LOG = org.slf4j.LoggerFactory.getLogger(ActivityAggregationService.class);
  private static final String ACTIVITY = "activity";
  private static final String DISTANCE = "distance";
  private static final String START_DATA = "startDate";
  private static final String TYPE = "type";
  private static final String YEAR = "year";
  private static final String MONTH = "month";
  private static final String WEEK = "week";
  private static final String TOTAL_AMOUNT = "totalAmount";
  private final MongoTemplate mongoTemplate;
  private final ObjectMapper objectMapper = new ObjectMapper();

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
            .as(YEAR)
            .and(DISTANCE)
            .as(DISTANCE),
        Aggregation.group(YEAR, MONTH).sum(DISTANCE).as(TOTAL_AMOUNT),
        Aggregation.project()
            .andExpression("_id.year")
            .as(YEAR)
            .andExpression("_id.month")
            .as(MONTH)
            .and(TOTAL_AMOUNT)
            .divide(1000)
            .as(TOTAL_AMOUNT),
        Aggregation.sort(Sort.Direction.ASC, YEAR, MONTH));
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
            .as(WEEK)
            .andExpression("{$month: '$" + START_DATA + "'}")
            .as(MONTH)
            .andExpression("{$year: '$" + START_DATA + "'}")
            .as(YEAR)
            .and(DISTANCE)
            .divide(1000)
            .as(DISTANCE),
        Aggregation.group(WEEK, MONTH, YEAR).sum(DISTANCE).as(TOTAL_AMOUNT),
        Aggregation.project()
            .andExpression("_id.year")
            .as(YEAR)
            .andExpression("_id.month")
            .as(MONTH)
            .andExpression("_id.week")
            .as(WEEK)
            .and(TOTAL_AMOUNT)
            .as(TOTAL_AMOUNT),
        Aggregation.sort(Sort.Direction.ASC, WEEK));
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

  public List<KilometersPerWeek> getKilometerPerWeekGoals(String type) {
    TypeReference<List<KilometersPerWeek>> listTypeReference = new TypeReference<>() {};
    List<KilometersPerWeek> kilometersPerWeek = new ArrayList<>();
    try {
      kilometersPerWeek =
          objectMapper.readValue(
              getFile(CLASSPATH_URL_PREFIX.concat("files/" + type + "_weekly_goals.json")),
              listTypeReference);
    } catch (IOException e) {
      LOG.error("Error mapping file", e);
    }
    return kilometersPerWeek; // TODO: Exception if empty
  }
}
