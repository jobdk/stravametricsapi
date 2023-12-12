package com.jobdk.stravametricsapi.model.aggregation;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class KilometersPerYear {
  @Id private int year;
  private Double totalAmount; // TODO: to Long -> all
}
