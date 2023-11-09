package com.jobdk.stravametricsapi.model.activity;

import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({"id", "summary_polyline", "resource_state"})
@Data
@NoArgsConstructor
public class Map implements Serializable {

  private static final long serialVersionUID = -3742168657266657826L;
  @JsonProperty("id")
  private String id;
  @JsonProperty("summary_polyline")
  private String summaryPolyline;
  @JsonProperty("resource_state")
  private Integer resourceState;
  @JsonIgnore private java.util.Map<String, Object> additionalProperties = new LinkedHashMap<>();

}
