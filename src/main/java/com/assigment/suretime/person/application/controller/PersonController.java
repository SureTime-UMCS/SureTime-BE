package com.assigment.suretime.person.application.controller;

import com.assigment.suretime.person.application.response.PersonDTO;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.models.RolesCollection;
import com.assigment.suretime.person.domain.service.PersonService;
import com.assigment.suretime.securityJwt.domain.authenticationFacade.IAuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final IAuthenticationFacade authenticationFacade;

    @GetMapping()
    public CollectionModel<EntityModel<Person>> all(){
        return personService.all();
    }

    @GetMapping("{email}")
    public ResponseEntity<?> one(@PathVariable String email){
        return personService.getByEmail(email);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(){
        return personService.getByEmail(authenticationFacade.getUserDetailsImpl().getEmail());
    }

    @PostMapping("")
    public ResponseEntity<?> addOne(@RequestBody PersonDTO personDTO) {
            return personService.updateOrCreate(personDTO);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody PersonDTO person){
            return personService.updateOrCreate(person);
    }

    @DeleteMapping("{email}")
    public ResponseEntity<?> removeOne(@PathVariable String email){
        return personService.removeOne(email);
    }

    @PutMapping("roles/{email}")
    public ResponseEntity<?> updateRolesForPerson(@PathVariable String email, @Valid @RequestBody RolesCollection roles){
        return personService.updateRoles(email, roles);
    }



}