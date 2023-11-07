package com.jobdk.stravametricsapi.model;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jobdk.stravametricsapi.model.activity.Activity;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class ActivityTest {

  String json =
      """
                   {
                      "resource_state": 2,
                      "athlete": {
                        "id": 10000000,
                        "resource_state": 1
                      },
                      "name": "Lauf am Nachmittag",
                      "distance": 900.0,
                      "moving_time": 4149,
                      "elapsed_time": 4432,
                      "total_elevation_gain": 166.0,
                      "type": "Run",
                      "sport_type": "Run",
                      "workout_type": null,
                      "id": 200000000,
                      "start_date": "2023-11-06T13:33:19Z",
                      "start_date_local": "2023-11-06T14:33:19Z",
                      "timezone": "(GMT+01:00) Europe/Berlin",
                      "utc_offset": 3600.0,
                      "location_city": null,
                      "location_state": null,
                      "location_country": null,
                      "achievement_count": 0,
                      "kudos_count": 0,
                      "comment_count": 0,
                      "athlete_count": 1,
                      "photo_count": 0,
                      "map": {
                        "id": "d394723874",
                        "summary_polyline": "polylinedummy",
                        "resource_state": 2
                      },
                      "trainer": false,
                      "commute": false,
                      "manual": false,
                      "private": true,
                      "visibility": "only_me",
                      "flagged": false,
                      "gear_id": null,
                      "start_latlng": [
                        50.0,
                        50.0
                      ],
                      "end_latlng": [
                        51.0,
                        51.0
                      ],
                      "average_speed": 2.000,
                      "max_speed": 4.000,
                      "average_cadence": 80.0,
                      "average_temp": 14,
                      "has_heartrate": true,
                      "average_heartrate": 130.0,
                      "max_heartrate": 140.0,
                      "heartrate_opt_out": false,
                      "display_hide_heartrate_option": true,
                      "elev_high": 800.0,
                      "elev_low": 700.0,
                      "upload_id": 3000000,
                      "upload_id_str": "3000000",
                      "external_id": "garmin_ping_dummy",
                      "from_accepted_tag": false,
                      "pr_count": 0,
                      "total_photo_count": 0,
                      "has_kudoed": false
                    }\
                  """;

  @Test
  void testActivityIsCorrectlyMapped() throws IOException {
    // Arrange
    // Act
    ObjectMapper objectMapper = new ObjectMapper();
    Activity activity = objectMapper.readValue(json, Activity.class);
    String result = objectMapper.writeValueAsString(activity);
    JsonElement jsonElement = JsonParser.parseString(json);
    JsonElement jsonElement1 = JsonParser.parseString(result);

    // Assert
    assertEquals(jsonElement, jsonElement1);
  }
}
