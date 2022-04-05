package com.assigment.suretime.person.models;

import com.assigment.suretime.club.Club;
import com.assigment.suretime.person.Gender;
import com.assigment.suretime.securityJwt.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
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

    @DocumentReference
    private Club club;

    @DocumentReference
    private Person coach;

    @DocumentReference
    @JsonIgnore
    private User user;

    @JsonIgnore
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created = LocalDateTime.now();

    //For deserialisation purposes Person must have a zero-arg constructor.
    public Person(){}

    @PersistenceConstructor
    public Person(String email){
        this.email = email;
    }

    /**
     * Annotation PersistenceConstructor is needed to mapping object when retrieved from db.
     */
    @org.springframework.data.annotation.PersistenceConstructor
    public Person(String id, String firstName, String secondName, String email,
                  Gender gender, Club club, Person coach, LocalDateTime created) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.club = club;
        this.coach = coach;
        this.created = LocalDateTime.now();

    }
    @Builder
    public Person(String firstName, String secondName, String email,
                  Gender gender, Club club, Person coach) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.club = club;
        this.coach = coach;
    }

    public Person(Person p) {
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.email = p.getEmail();
        this.gender = p.getGender();
        this.club = p.getClub();
        this.coach = p.getCoach();
        this.user = p.getUser();
    }

    public void update(Person p) {
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.email = p.getEmail();
        this.gender = p.getGender();
        this.club = p.getClub();
        this.coach = p.getCoach();
        this.user = p.getUser();
    }

}
