package com.assigment.suretime.event;

import com.assigment.suretime.person.Person;
import heat.Heat;
import jdk.jfr.Percentage;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document
public class Event {

    @DBRef
    private List<Person> competitors;

    @DBRef
    private List<Heat> heats;

}
