package com.jobdk.stravametricsapi.model.aggregation;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class KilometersPerWeek {
  private int week;
  private int month;
  private int year;
  private Double totalAmount;
}
