package com.assigment.suretime.heat;

import com.assigment.suretime.person.Person;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document
public class Heat {
    @Id
    private String id;

    private LocalDateTime startTime;

    @DBRef
    List<Person> competitors;

    Map<Person, Float> results;

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
