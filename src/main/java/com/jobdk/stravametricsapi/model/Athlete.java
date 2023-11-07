package com.jobdk.stravametricsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({"id", "resource_state"})
@Getter
@Setter
@NoArgsConstructor
public class Athlete implements Serializable {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("resource_state")
  private Integer resourceState;

  @JsonIgnore private Map<String, Object> additionalProperties = new LinkedHashMap<>();

  private static final long serialVersionUID = 7427781649520850142L;

  /**
   * @param resourceState
   * @param id
   */
  public Athlete(Integer id, Integer resourceState) {
    super();
    this.id = id;
    this.resourceState = resourceState;
  }
}
