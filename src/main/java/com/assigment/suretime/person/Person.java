package com.assigment.suretime.person;

import com.assigment.suretime.club.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @DBRef
    private Club club;

    @DBRef
    private Person coach;


    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime created = LocalDateTime.now();


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
        this.created = created;

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
    }

}
