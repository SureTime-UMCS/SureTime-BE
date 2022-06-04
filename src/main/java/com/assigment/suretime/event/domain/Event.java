package com.assigment.suretime.event.domain;

import com.assigment.suretime.generics.MongoModel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document
@NoArgsConstructor
@Getter
@Setter
public class Event implements MongoModel {

    @Id
    private String id;

    private String name;

    private Set<String> competitorsUuid;

    private Set<String> heatsId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @PersistenceConstructor
    public Event(String id, String name, Set<String> competitorsUuid,
                 Set<String> heatsId, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.competitorsUuid = competitorsUuid == null ? new HashSet<>(): competitorsUuid;
        this.heatsId = heatsId == null ? new HashSet<>(): heatsId;
        this.startTime = startTime;
    }

    @PersistenceConstructor
    public Event(String name) {
        this(null, name, null, null, null);
    }

    @PersistenceConstructor
    public Event(String name, LocalDateTime startTime) {
        this(null, name, null, null, startTime);
    }

    @PersistenceConstructor
    public Event(String name, LocalDateTime startTime, Set<String> competitorsUuid, Set<String> heatsId) {
        this(null, name, competitorsUuid, heatsId, startTime);
    }
    @Override
    public void updateNotNullFields(Object o) {
        Event event = (Event)o;
        this.competitorsUuid = event.getCompetitorsUuid() != null ? event.getCompetitorsUuid(): this.competitorsUuid;
        this.heatsId = event.getHeatsId() != null ? event.getHeatsId() : this.heatsId;
        this.startTime = event.getStartTime()!= null ? event.getStartTime() : this.startTime;
        this.name = event.getName() != null ? event.getName() : this.name;
    }
    public void addHeat(String heatId) {
        if(this.heatsId == null){
            this.heatsId = new HashSet<>();
        }
        this.heatsId.add(heatId);
    }
}