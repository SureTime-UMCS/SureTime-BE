package com.assigment.suretime.person.domain;

import com.assigment.suretime.person.application.response.PersonDTO;
import com.assigment.suretime.person.domain.models.Person;

public class utils {
    public static PersonDTO createDTOFromPerson(Person person) {
        return new PersonDTO(person);
    }
}
