package com.assigment.suretime.event.application.response;

import com.assigment.suretime.event.domain.Event;
import com.assigment.suretime.generics.MongoDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class EventDto implements MongoDto {

    private String id;

    private String name;

    private Set<String> competitorsUuid;

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
        this.competitorsUuid = new HashSet<>();
    }
    public EventDto(Event event){
        this.name = event.getName();
        this.heatsId = event.getHeatsId();
        this.competitorsUuid = event.getCompetitorsUuid();
        this.id = event.getId();
        this.startTime = event.getStartTime();
    }

    @PersistenceConstructor
    public EventDto(String name, Set<String> competitorsUuid, Set<String> heatsId, LocalDateTime startTime) {
        this.name = name;
        this.competitorsUuid = competitorsUuid;
        this.startTime = startTime;
        this.heatsId = heatsId;
    }
}