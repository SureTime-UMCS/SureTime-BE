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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class EventDto implements MongoDto {

    private String id;

    private String name;

    private List<String> competitorsEmail;

    private List<String> heatsId;

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
        this.heatsId = new ArrayList<>();
        this.competitorsEmail = new ArrayList<>();
    }

    @PersistenceConstructor
    public EventDto(String name, List<String> competitorsEmail, List<String> heatsId, LocalDateTime startTime) {
        this.name = name;
        this.competitorsEmail = competitorsEmail;
        this.startTime = startTime;
        this.heatsId = heatsId;
    }

}