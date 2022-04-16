package com.assigment.suretime.competition.models;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.generics.MongoModel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Competition implements MongoModel {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private Address address;

    private Set<String> eventsId;

    private Set<String> competitors;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime, endTime;

    @PersistenceConstructor
    public Competition(String id, String name, Address address, Set<String> eventsId, Set<String> competitors, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.eventsId = eventsId;
        this.competitors = competitors;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public CompetitionDto toDto(){
        CompetitionDto competitionDto = new CompetitionDto();
        competitionDto.setCompetitors(this.getCompetitors());
        competitionDto.setAddress(this.getAddress());
        competitionDto.setEventsId(this.getEventsId());
        competitionDto.setId(this.getId());
        competitionDto.setName(this.getName());
        competitionDto.setStartTime(this.getStartTime());
        competitionDto.setEndTime(this.getEndTime());
        return competitionDto;

    }

    @Override
    public void updateNotNullFields(Object o) {
        Competition model = (Competition)o;
        this.competitors = model.getCompetitors() != null ? model.getCompetitors() : this.competitors;
        this.eventsId = model.getEventsId() != null ? model.getEventsId() : this.eventsId;
        this.name = model.getName() != null ? model.getName() : this.name;
        this.startTime = model.getStartTime() != null ? model.getStartTime() : this.startTime;
        this.endTime = model.getEndTime() != null ? model.getEndTime() : this.endTime;
        this.address = model.getAddress() != null ? model.getAddress() : this.address;

    }

    public void addEvent(String eventId) {
        this.eventsId.add(eventId);
    }
}
