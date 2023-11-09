package com.jobdk.stravametricsapi.model.activity;

import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonPropertyOrder({
  "resource_state",
  "athlete",
  "name",
  "distance",
  "moving_time",
  "elapsed_time",
  "total_elevation_gain",
  "type",
  "sport_type",
  "workout_type",
  "id",
  "start_date",
  "start_date_local",
  "timezone",
  "utc_offset",
  "location_city",
  "location_state",
  "location_country",
  "achievement_count",
  "kudos_count",
  "comment_count",
  "athlete_count",
  "photo_count",
  "map",
  "trainer",
  "commute",
  "manual",
  "private",
  "visibility",
  "flagged",
  "gear_id",
  "start_latlng",
  "end_latlng",
  "average_speed",
  "max_speed",
  "average_cadence",
  "average_temp",
  "has_heartrate",
  "average_heartrate",
  "max_heartrate",
  "heartrate_opt_out",
  "display_hide_heartrate_option",
  "elev_high",
  "elev_low",
  "upload_id",
  "upload_id_str",
  "external_id",
  "from_accepted_tag",
  "pr_count",
  "total_photo_count",
  "has_kudoed"
})
@Data
@Document
@NoArgsConstructor
public class Activity implements Serializable {

  private static final long serialVersionUID = -7804857919967216209L;

  @Id
  @JsonProperty("id")
  private Long id;

  @JsonProperty("resource_state")
  private Integer resourceState;

  @JsonProperty("athlete")
  private Athlete athlete;

  @JsonProperty("name")
  private String name;

  @JsonProperty("distance")
  private Double distance;

  @JsonProperty("moving_time")
  private Integer movingTime;

  @JsonProperty("elapsed_time")
  private Integer elapsedTime;

  @JsonProperty("total_elevation_gain")
  private Double totalElevationGain;

  @JsonProperty("type")
  private String type;

  @JsonProperty("sport_type")
  private String sportType;

  @JsonProperty("workout_type")
  private String workoutType;

  @JsonProperty("start_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  @Indexed(direction = IndexDirection.DESCENDING)
  private Date startDate;

  @JsonProperty("start_date_local")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private Date startDateLocal;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("utc_offset")
  private Double utcOffset;

  @JsonProperty("location_city")
  private String locationCity;

  @JsonProperty("location_state")
  private String locationState;

  @JsonProperty("location_country")
  private String locationCountry;

  @JsonProperty("achievement_count")
  private Integer achievementCount;

  @JsonProperty("kudos_count")
  private Integer kudosCount;

  @JsonProperty("comment_count")
  private Integer commentCount;

  @JsonProperty("athlete_count")
  private Integer athleteCount;

  @JsonProperty("photo_count")
  private Integer photoCount;

  @JsonProperty("map")
  private com.jobdk.stravametricsapi.model.activity.Map map;

  @JsonProperty("trainer")
  private Boolean trainer;

  @JsonProperty("commute")
  private Boolean commute;

  @JsonProperty("manual")
  private Boolean manual;

  @JsonProperty("private")
  private Boolean _private;

  @JsonProperty("visibility")
  private String visibility;

  @JsonProperty("flagged")
  private Boolean flagged;

  @JsonProperty("gear_id")
  private Long gearId;

  @JsonProperty("start_latlng")
  private List<Double> startLatlng;

  @JsonProperty("end_latlng")
  private List<Double> endLatlng;

  @JsonProperty("average_speed")
  private Double averageSpeed;

  @JsonProperty("max_speed")
  private Double maxSpeed;

  @JsonProperty("average_cadence")
  private Double averageCadence;

  @JsonProperty("average_temp")
  private Integer averageTemp;

  @JsonProperty("has_heartrate")
  private Boolean hasHeartrate;

  @JsonProperty("average_heartrate")
  private Double averageHeartrate;

  @JsonProperty("max_heartrate")
  private Double maxHeartrate;

  @JsonProperty("heartrate_opt_out")
  private Boolean heartrateOptOut;

  @JsonProperty("display_hide_heartrate_option")
  private Boolean displayHideHeartrateOption;

  @JsonProperty("elev_high")
  private Double elevHigh;

  @JsonProperty("elev_low")
  private Double elevLow;

  @JsonProperty("upload_id")
  private Long uploadId;

  @JsonProperty("upload_id_str")
  private String uploadIdStr;

  @JsonProperty("external_id")
  private String externalId;

  @JsonProperty("from_accepted_tag")
  private Boolean fromAcceptedTag;

  @JsonProperty("pr_count")
  private Integer prCount;

  @JsonProperty("total_photo_count")
  private Integer totalPhotoCount;

  @JsonProperty("has_kudoed")
  private Boolean hasKudoed;

  @JsonIgnore private Map<String, Object> additionalProperties = new LinkedHashMap<>();
}
