package com.assigment.suretime.heat;

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
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Document
@NoArgsConstructor
public class Heat implements MongoModel {
    @Id
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @DocumentReference(lazy = true)
    List<Person> competitors;
    Map<Person, Float> results;

    @PersistenceConstructor
    public Heat(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Heat(String id, LocalDateTime startTime, List<Person> competitors, Map<Person, Float> results) {
        this.id = id;
        this.startTime = startTime;
        this.competitors = competitors;
        this.results = results;
    }
    public void update(Heat heat){
        this.startTime = heat.getStartTime();
        this.competitors = heat.getCompetitors();
        this.results = heat.getResults();
    }

    public void addResult(Person person, Float result){
        results.put(person, result);
    }
    public void addCompetitor(Person person){
        competitors.add(person);
    }
    public void removeCompetitor(Person person){
        competitors.remove(person);
    }


}
