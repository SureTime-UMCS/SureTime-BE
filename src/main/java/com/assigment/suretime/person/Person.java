package com.assigment.suretime.person;

import com.assigment.suretime.address.Address;

import com.assigment.suretime.club.Club;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document
public class Person {
    //In mongodb in id is by default String.
    @Id
    private String id;
    private String firstName;
    private String secondName;
    @Indexed(unique = true)
    private String email;
    private Gender gender;
    private Address address;

    @DocumentReference(lazy = true)
    private Club club;
    @DocumentReference(lazy = true)
    private Person coach;
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created;

    public Person(String firstName, String secondName, Gender gender, String email, Address address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.created = LocalDateTime.now();
    }

}
