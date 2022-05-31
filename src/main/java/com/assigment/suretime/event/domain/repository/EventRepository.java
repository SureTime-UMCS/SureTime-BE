package com.assigment.suretime.event.domain.repository;

import com.assigment.suretime.event.domain.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends MongoRepository<Event, String> {
}
