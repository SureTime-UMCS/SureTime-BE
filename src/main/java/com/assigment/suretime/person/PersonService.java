package com.assigment.suretime.person;


import com.assigment.suretime.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonModelAssembler personAssembler;


    public CollectionModel<EntityModel<Person>> all(){
        return personAssembler.toCollectionModel(personRepository.findAll());
    }

    public ResponseEntity<EntityModel<Person>> getByEmail(String email){
       return personRepository.findByEmail(email)
               .map(personAssembler::toModel).map(ResponseEntity::ok)
               .orElseThrow(()-> new NotFoundException("Person", email));

    }

    public ResponseEntity<EntityModel<Person>> addOne(Person person) {
        //if person is found then return response with 203 status (See other)
        //else create new person.
        return personRepository.findByEmail(person.getEmail()).
                map(p -> new ResponseEntity<>(personAssembler.toModel(p), HttpStatus.SEE_OTHER))
                .orElseGet(()->
                {
                    Person newPerson = new Person(person);
                    EntityModel<Person> entityModel = personAssembler.toModel(personRepository.insert(newPerson));
                    return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
                });

    }


    ResponseEntity<EntityModel<Person>> updateCoach(String personEmail, String coachEmail){
        var coach = personRepository.findByEmail(coachEmail).
                orElseThrow(()-> new NotFoundException("Person", coachEmail));
        var person = personRepository.findByEmail(personEmail).
                orElseThrow(()-> new NotFoundException("Person", personEmail));
        person.setCoach(coach);
        var updatedPerson = personRepository.save(person);

        return ResponseEntity.ok(personAssembler.toModel(updatedPerson));


    }

}
