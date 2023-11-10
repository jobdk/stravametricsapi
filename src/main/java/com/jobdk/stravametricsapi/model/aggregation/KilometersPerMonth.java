package com.jobdk.stravametricsapi.model.aggregation;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class KilometersPerMonth {
  private String month;
  private String year;
  private String totalAmount;
}
