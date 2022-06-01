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

    private final PersonService domainPersonService;

    private final IAuthenticationFacade authenticationFacade;

    @GetMapping()
    public CollectionModel<EntityModel<Person>> all(){
        return domainPersonService.all();
    }

    @GetMapping("{email}")
    public ResponseEntity<?> one(@PathVariable String email){
        return domainPersonService.getByEmail(email);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(){
        return domainPersonService.getByEmail(authenticationFacade.getUserDetailsImpl().getEmail());
    }

    @PostMapping("")
    public ResponseEntity<?> addOne(@RequestBody PersonDTO personDTO) {
            return domainPersonService.updateOrCreate(personDTO);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody PersonDTO person){
            return domainPersonService.updateOrCreate(person);
    }

    @DeleteMapping("{email}")
    public ResponseEntity<?> removeOne(@PathVariable String email){
        return domainPersonService.removeOne(email);
    }

    @PutMapping("roles/{email}")
    public ResponseEntity<?> updateRolesForPerson(@PathVariable String email, @Valid @RequestBody RolesCollection roles){
        return domainPersonService.updateRoles(email, roles);
    }



}
