package com.assigment.suretime.competition;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.event.Event;
import com.assigment.suretime.generics.MongoModel;
import com.assigment.suretime.person.models.Person;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document
@NoArgsConstructor
public class Competition implements MongoModel<Competition> {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private Address address;
    @DocumentReference(lazy = true)
    private List<Event> events;

    @DocumentReference(lazy = true)
    private Map<String,Person> competitors;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime, endTime;

    @Builder
    @PersistenceConstructor
    public Competition(String id, String name, Address address, List<Event> events, Map<String, Person> competitors, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.events = events;
        this.competitors = competitors;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @PersistenceConstructor
    public Competition(String name, Address address, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.events = new ArrayList<>();
        this.competitors = new HashMap<>();
    }

    public static CompetitionDto toDto(Competition competition){
        //FIXME: make it work if need.
        CompetitionDto competitionDto = new CompetitionDto();
        return competitionDto;

    }

    @Override
    public void updateNotNullFields(Competition model) {
        this.competitors = model.getCompetitors() != null ? model.getCompetitors() : this.competitors;
        this.events = model.getEvents() != null ? model.getEvents() : this.events;
        this.name = model.getName() != null ? model.getName() : this.name;
        this.startTime = model.getStartTime() != null ? model.getStartTime() : this.startTime;
        this.endTime = model.getEndTime() != null ? model.getEndTime() : this.endTime;
        this.address = model.getAddress() != null ? model.getAddress() : this.address;

    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
