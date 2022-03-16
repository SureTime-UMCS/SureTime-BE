package com.assigment.suretime.person;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonModelAssembler personAssembler;

    @GetMapping()
    public CollectionModel<EntityModel<Person>> all(){
        List<EntityModel<Person>> persons = personService.all().stream().map(personAssembler::toModel).toList();

        return CollectionModel.of(persons,
                linkTo(methodOn(PersonController.class).all()).withSelfRel());

    }

    @GetMapping("{email}")
    public ResponseEntity<EntityModel<Person>> one(@PathVariable String email){
        return personService.getByEmail(email);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Person>> addOne(@RequestBody Person person){
        return personService.addOne(person);
    }

    @PutMapping("{personEmail}")
    public ResponseEntity<EntityModel<Person>> updateCoach(@PathVariable String personEmail, @RequestBody String coachEmail){
        return personService.updateCoach(personEmail, coachEmail);
    }
}
