package com.assigment.suretime.person;

import com.assigment.suretime.club.Club;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
public class PersonDTO {
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
    private PersonDTO coach;

    public PersonDTO(String email){
        this.email = email;
    }

    /**
     * Annotation PersistenceConstructor is needed to mapping object when retrieved from db.
     */
    @org.springframework.data.annotation.PersistenceConstructor
    public PersonDTO(String id, String firstName, String secondName, String email,
                     Gender gender, Club club, PersonDTO coach) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.club = club;
        this.coach = coach;

    }
    @Builder
    public PersonDTO(String firstName, String secondName, String email,
                     Gender gender, Club club, PersonDTO coach) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.club = club;
        this.coach = coach;
    }

    public PersonDTO(PersonDTO p) {
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.email = p.getEmail();
        this.gender = p.getGender();
        this.club = p.getClub();
        this.coach = p.getCoach();
    }

}
