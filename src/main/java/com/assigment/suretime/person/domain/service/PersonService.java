package com.assigment.suretime.person.domain.service;
import com.assigment.suretime.person.application.response.PersonDTO;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.models.RolesCollection;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    private Person createPersonFromDTO(PersonDTO personDTO) {
        return null;
    }

    CollectionModel<EntityModel<Person>> all();

    ResponseEntity<?> getByEmail(String email);

    ResponseEntity<?> getByUUID(String email);

    ResponseEntity<?> removeOne(String email);

    ResponseEntity<?> updateOrCreate(Person person);

    ResponseEntity<?> updateOrCreate(PersonDTO personDTO);

    ResponseEntity<?> updateCoach(String personUUID, String coachUUID);

    ResponseEntity<?> updateRoles(String uuid, RolesCollection newRoles);
}
