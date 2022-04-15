package com.assigment.suretime.event;

import com.assigment.suretime.generics.MongoDto;
import com.assigment.suretime.generics.MongoModel;
import com.assigment.suretime.heat.models.Heat;
import com.assigment.suretime.person.models.Person;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class EventDto implements MongoDto {

    private String id;

    private String name;

    private Set<String> competitorsEmail;

    private Set<String> heatsId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @PersistenceConstructor
    public EventDto(String name) {
        this.name = name;
    }

    @PersistenceConstructor
    public EventDto(String name, LocalDateTime startTime) {
        this.name = name;
        this.startTime = startTime;
        this.heatsId = new HashSet<>();
        this.competitorsEmail = new HashSet<>();
    }
    public EventDto(Event event){
        this.name = event.getName();
        this.heatsId = event.getHeatsId();
        this.competitorsEmail = event.getCompetitorsEmail();
        this.id = event.getId();
        this.startTime = event.getStartTime();
    }

    @PersistenceConstructor
    public EventDto(String name, Set<String> competitorsEmail, Set<String> heatsId, LocalDateTime startTime) {
        this.name = name;
        this.competitorsEmail = competitorsEmail;
        this.startTime = startTime;
        this.heatsId = heatsId;
    }




}