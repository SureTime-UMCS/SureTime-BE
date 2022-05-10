package com.assigment.suretime.heat.models;

import com.assigment.suretime.generics.MongoModel;
import com.assigment.suretime.person.models.Person;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Heat implements MongoModel {
    @Id
    private String id;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    List<String> competitors;

    Map<String, String> results;

    @PersistenceConstructor
    public Heat(String name, LocalDateTime startTime) {
        this.name = name;
        this.startTime = startTime;
        this.competitors = new ArrayList<>();
        this.results = new HashMap<>();
    }

    public Heat(String id,String name, LocalDateTime startTime, List<String> competitors, Map<String, String> results) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.competitors = competitors;
        this.results = results;
    }

    @Override
    public void updateNotNullFields(Object o){
        Heat heat = (Heat) o;
        this.startTime = heat.getStartTime() != null ?  heat.getStartTime() : this.startTime;
        this.name = heat.getName() != null ? heat.getName(): this.name;
        this.competitors = heat.getCompetitors() != null? heat.getCompetitors() : this.competitors;
        this.results = heat.getResults() != null ? heat.getResults() : this.results;
    }

    public HeatDto toDto(){
        HeatDto dto = new HeatDto();
        dto.setName(this.getName());
        dto.setId(this.getId());
        dto.setStartTime(this.getStartTime());
        var results = this.getResults();
        Map<String, String> resultDto= new HashMap<>();
        if(results!= null){
            resultDto.putAll(results);
        }
        dto.setResults(resultDto);
        dto.setCompetitorsEmail(this.getCompetitors());
        return dto;
    }
}
