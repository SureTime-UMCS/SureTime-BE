package com.assigment.suretime.event;

import com.assigment.suretime.generics.MongoModel;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.heat.models.Heat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

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
    public void updateNotNullFields(Event event) {
        this.competitors = event.getCompetitors() != null ? event.getCompetitors(): this.competitors;
        this.heats = event.getHeats() != null ? event.getHeats() : this.heats;
        this.startTime = event.getStartTime()!= null ? event.getStartTime() : this.startTime;
        this.name = event.getName() != null ? event.getName() : this.name;
    }
    public void addHeat(Heat heat) {
        if(this.heats == null){
            this.heats = new ArrayList<>();
        }
        this.heats.add(heat);
    }
}