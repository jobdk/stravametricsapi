package com.jobdk.stravametricsapi.service;

import com.jobdk.stravametricsapi.model.activity.Activity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

// First parameter is the entity type, second parameter is the type of the entity's ID field
public interface ActivityRepository extends MongoRepository<Activity, Long> {
  Optional<List<Activity>> findActivitiesByOrderByStartDateDesc();
}
