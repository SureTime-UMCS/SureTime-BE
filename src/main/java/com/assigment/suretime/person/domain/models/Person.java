package com.assigment.suretime.person.domain.models;

import com.assigment.suretime.securityJwt.domain.models.User;
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
@AllArgsConstructor
public class Person {
    @Id
    private String id;
    private String firstName;
    private String secondName;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String userUUID;

    private Gender gender;
    private String clubUUID;

    @DocumentReference
    private Person coach;

    @DocumentReference
    @JsonIgnore
    private User user;

    @JsonIgnore
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created = LocalDateTime.now();

    @PersistenceConstructor
    public Person(String email){
        this.email = email;
    }

    public Person(String id, String firstName, String secondName, String email,
                  Gender gender, String club, Person coach) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.clubUUID = club;
        this.coach = coach;
        this.created = LocalDateTime.now();

    }
    @Builder
    public Person(String firstName, String secondName, String email,
                  Gender gender, String club, Person coach) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.clubUUID = club;
        this.coach = coach;
    }

    public Person(Person p) {
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.email = p.getEmail();
        this.gender = p.getGender();
        this.clubUUID = p.getClubUUID();
        this.coach = p.getCoach();
        this.user = p.getUser();
    }

    public void update(Person p) {
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.email = p.getEmail();
        this.gender = p.getGender();
        this.clubUUID = p.getClubUUID();
        this.coach = p.getCoach();
        this.user = p.getUser();
    }

}
