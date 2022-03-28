package com.assigment.suretime.person;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping()
    public CollectionModel<EntityModel<Person>> all(){
        return personService.all();
    }

    @GetMapping("{email}")
    public ResponseEntity<EntityModel<Person>> one(@PathVariable String email){
        return personService.getByEmail(email);
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Person>> addOne(@RequestBody Person person){
        return personService.addOne(person);
    }

    @PutMapping("{personEmail}")
    public ResponseEntity<EntityModel<Person>> updateCoach(@PathVariable String personEmail, @RequestBody String coachEmail){
        return personService.updateCoach(personEmail, coachEmail);
    }
}
