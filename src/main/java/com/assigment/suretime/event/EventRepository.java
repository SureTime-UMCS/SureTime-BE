package com.assigment.suretime.event;

import com.assigment.suretime.competition.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface EventRepository extends MongoRepository<Event, String> {
    void deleteByName(String name);
}
