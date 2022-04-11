package com.assigment.suretime.person.models;

import com.assigment.suretime.person.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {

    private String firstName;
    private String secondName;
    private String email;
    private Gender gender;
    private String clubName;
    private String coachEmail;

    @PersistenceConstructor
    @Builder
    public PersonDTO(String firstName, String secondName, String email, Gender gender, String clubName, String coachEmail) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.clubName = clubName;
        this.coachEmail = coachEmail;
    }

    public PersonDTO(Person person) {
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.email = person.getEmail();
        this.gender = person.getGender();
        this.clubName = person.getClub() != null ? person.getClub().getName(): null;
        this.coachEmail =person.getCoach() != null ? person.getCoach().getEmail(): null;
    }
}
