package com.assigment.suretime.person.application.response;

import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.models.Gender;
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
    private String clubUUID;
    private String coachUUID;

    @PersistenceConstructor
    @Builder
    public PersonDTO(String firstName, String secondName, String email, Gender gender, String clubName, String coachEmail) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.gender = gender;
        this.clubUUID = clubName;
        this.coachUUID = coachEmail;
    }

    public PersonDTO(Person person) {
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.email = person.getEmail();
        this.gender = person.getGender();
        this.clubUUID = person.getClubUUID();
        this.coachUUID =person.getCoachUUID();
    }
}
