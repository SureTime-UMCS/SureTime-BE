package com.assigment.suretime.heat.application.response;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class HeatDto implements MongoDto {

    private String id;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    List<String> competitorsUUID;

    Map<String, String> results;

    @PersistenceConstructor
    public HeatDto(String name, LocalDateTime startTime) {
        this.name = name;
        this.startTime = startTime;
        this.competitorsUUID = new ArrayList<>();
        this.results = new HashMap<>();
    }

    public HeatDto(String id, String name, LocalDateTime startTime,
                   List<String> competitorsUUID,
                   Map<String, String> results) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.competitorsUUID = competitorsUUID;
        this.results = results;
    }

}
