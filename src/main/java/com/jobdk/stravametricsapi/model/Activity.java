package com.jobdk.stravametricsapi.model;

import com.fasterxml.jackson.annotation.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class Activity implements Serializable {

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

  @JsonProperty("id")
  private Long id;

  @JsonProperty("start_date")
  private String startDate;

  @JsonProperty("start_date_local")
  private String startDateLocal;

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
  private com.jobdk.stravametricsapi.model.Map map;

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

  private static final long serialVersionUID = -7804857919967216209L;

  // SuppressWarnings("java:S107")
  public Activity(
      Integer resourceState,
      Athlete athlete,
      String name,
      Double distance,
      Integer movingTime,
      Integer elapsedTime,
      Double totalElevationGain,
      String type,
      String sportType,
      String workoutType,
      Long id,
      String startDate,
      String startDateLocal,
      String timezone,
      Double utcOffset,
      String locationCity,
      String locationState,
      String locationCountry,
      Integer achievementCount,
      Integer kudosCount,
      Integer commentCount,
      Integer athleteCount,
      Integer photoCount,
      com.jobdk.stravametricsapi.model.Map map,
      Boolean trainer,
      Boolean commute,
      Boolean manual,
      Boolean _private,
      String visibility,
      Boolean flagged,
      Long gearId,
      List<Double> startLatlng,
      List<Double> endLatlng,
      Double averageSpeed,
      Double maxSpeed,
      Double averageCadence,
      Integer averageTemp,
      Boolean hasHeartrate,
      Double averageHeartrate,
      Double maxHeartrate,
      Boolean heartrateOptOut,
      Boolean displayHideHeartrateOption,
      Double elevHigh,
      Double elevLow,
      Long uploadId,
      String uploadIdStr,
      String externalId,
      Boolean fromAcceptedTag,
      Integer prCount,
      Integer totalPhotoCount,
      Boolean hasKudoed) {
    super();
    this.resourceState = resourceState;
    this.athlete = athlete;
    this.name = name;
    this.distance = distance;
    this.movingTime = movingTime;
    this.elapsedTime = elapsedTime;
    this.totalElevationGain = totalElevationGain;
    this.type = type;
    this.sportType = sportType;
    this.workoutType = workoutType;
    this.id = id;
    this.startDate = startDate;
    this.startDateLocal = startDateLocal;
    this.timezone = timezone;
    this.utcOffset = utcOffset;
    this.locationCity = locationCity;
    this.locationState = locationState;
    this.locationCountry = locationCountry;
    this.achievementCount = achievementCount;
    this.kudosCount = kudosCount;
    this.commentCount = commentCount;
    this.athleteCount = athleteCount;
    this.photoCount = photoCount;
    this.map = map;
    this.trainer = trainer;
    this.commute = commute;
    this.manual = manual;
    this._private = _private;
    this.visibility = visibility;
    this.flagged = flagged;
    this.gearId = gearId;
    this.startLatlng = startLatlng;
    this.endLatlng = endLatlng;
    this.averageSpeed = averageSpeed;
    this.maxSpeed = maxSpeed;
    this.averageCadence = averageCadence;
    this.averageTemp = averageTemp;
    this.hasHeartrate = hasHeartrate;
    this.averageHeartrate = averageHeartrate;
    this.maxHeartrate = maxHeartrate;
    this.heartrateOptOut = heartrateOptOut;
    this.displayHideHeartrateOption = displayHideHeartrateOption;
    this.elevHigh = elevHigh;
    this.elevLow = elevLow;
    this.uploadId = uploadId;
    this.uploadIdStr = uploadIdStr;
    this.externalId = externalId;
    this.fromAcceptedTag = fromAcceptedTag;
    this.prCount = prCount;
    this.totalPhotoCount = totalPhotoCount;
    this.hasKudoed = hasKudoed;
  }
}
