package com.jobdk.stravametricsapi.model;

import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({"id", "summary_polyline", "resource_state"})
@Getter
@Setter
@NoArgsConstructor
public class Map implements Serializable {

  @JsonProperty("id")
  private String id;

  @JsonProperty("summary_polyline")
  private String summaryPolyline;

  @JsonProperty("resource_state")
  private Integer resourceState;

  @JsonIgnore private java.util.Map<String, Object> additionalProperties = new LinkedHashMap<>();

  private static final long serialVersionUID = -3742168657266657826L;

  /**
   * @param resourceState
   * @param id
   * @param summaryPolyline
   */
  public Map(String id, String summaryPolyline, Integer resourceState) {
    super();
    this.id = id;
    this.summaryPolyline = summaryPolyline;
    this.resourceState = resourceState;
  }
}
