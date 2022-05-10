package com.assigment.suretime.event;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends MongoRepository<Event, String> {
}
