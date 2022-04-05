package com.assigment.suretime.competition;

import com.assigment.suretime.event.Event;
import com.assigment.suretime.person.models.Person;
import lombok.Data;
import org.apache.tomcat.jni.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document
public class Competition {
    @Id
    private String id;
    private Address address;
    private List<Event> events;

    @DBRef
    private Map<String,Person> competitors;
    private LocalDateTime startTime, endTime;


    boolean addCompetitor(Person person){
        //put returns previous value associated with key if there was in map already.
        //returns null when key does not exist in map.
         Person previousValue = competitors.put(person.getEmail(), person);
         return previousValue == null;
    }

    void addEvent(Event event){
        events.add(event);
    }
}
