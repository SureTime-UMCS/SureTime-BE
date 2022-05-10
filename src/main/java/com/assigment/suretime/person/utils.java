package com.assigment.suretime.person;

import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.models.PersonDTO;

public class utils {
    public static PersonDTO createDTOFromPerson(Person person) {
        return new PersonDTO(person);
    }
}
