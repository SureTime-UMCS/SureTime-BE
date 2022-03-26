package com.assigment.suretime.club;

import com.assigment.suretime.address.Address;
import com.assigment.suretime.person.Person;
import lombok.Builder;
import lombok.Data;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Club {
    @Id
    private String id;
    private Address address;

    @Indexed(unique = true)
    private String name;

    @DocumentReference
    private List<Person> members;

    private final LocalDateTime created = LocalDateTime.now();

    public Club(Address address, String name) {
        this.address = address;
        this.name = name;
    }

}
