package com.contest.bouldering.repository;

import com.contest.bouldering.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

    boolean existsByName(String name);

}
