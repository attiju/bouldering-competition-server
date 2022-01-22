package com.contest.bouldering.repository;

import com.contest.bouldering.model.Climber;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClimberRepository extends MongoRepository<Climber, String> {

    List<Climber> findAllByEventId(String eventId);

    void deleteAllByEventId(String eventId);
}
