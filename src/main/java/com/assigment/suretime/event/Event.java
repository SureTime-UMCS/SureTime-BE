package com.assigment.suretime.event;

import com.assigment.suretime.generics.MongoModel;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.heat.Heat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@NoArgsConstructor
@Getter
@Setter
public class Event implements MongoModel<Event> {

    @Id
    private String id;

    private String name;

    @DBRef(lazy = true)
    private List<Person> competitors;

    @DBRef(lazy = true)
    private List<Heat> heats;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @PersistenceConstructor
    public Event(String name) {
        this.name = name;
    }

    @PersistenceConstructor
    public Event(String name, LocalDateTime startTime) {
        this.name = name;
        this.startTime = startTime;
        this.heats = new ArrayList<>();
        this.competitors = new ArrayList<>();
    }

    @PersistenceConstructor
    public Event(String name, List<Person> competitors, List<Heat> heats, LocalDateTime startTime) {
        this.name = name;
        this.competitors = competitors;
        this.startTime = startTime;
        this.heats = heats;
    }
    @Override
    public void update(Event event) {
        this.competitors = event.getCompetitors();
        this.heats = event.getHeats();
        this.startTime = event.getStartTime();
        this.name = event.getName();
    }

    public void addHeat(Heat heat) {
        if(this.heats == null){
            this.heats = new ArrayList<>();
        }
        this.heats.add(heat);
    }
}