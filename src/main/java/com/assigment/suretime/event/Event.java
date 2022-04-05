package com.assigment.suretime.event;

import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.heat.Heat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Event {

    @DBRef
    private List<Person> competitors;

    @DBRef
    private List<Heat> heats;

}
