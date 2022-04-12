package com.assigment.suretime.event;

import com.assigment.suretime.competition.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    void deleteByName(String name);
}
