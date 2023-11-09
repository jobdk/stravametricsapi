package com.jobdk.stravametricsapi.model.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({"id", "resource_state"})
@Data
@NoArgsConstructor
public class Athlete implements Serializable {

  private static final long serialVersionUID = 7427781649520850142L;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("resource_state")
  private Integer resourceState;

  @JsonIgnore private Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
