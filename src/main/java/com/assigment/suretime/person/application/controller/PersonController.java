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

    @GetMapping("{uuid}")
    public ResponseEntity<?> one(@PathVariable String uuid){
        return domainPersonService.getByUUID(uuid);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(){
        return domainPersonService.getByUUID(authenticationFacade.getUserDetailsImpl().getUserUUID());
    }

    @PostMapping("")
    public ResponseEntity<?> addOne(@RequestBody PersonDTO personDTO) {
            return domainPersonService.updateOrCreate(personDTO);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody PersonDTO person){
            return domainPersonService.updateOrCreate(person);
    }

    @PutMapping("{personUUID}/coach/{coachUUID}")
    public ResponseEntity<?> updateCoach(@PathVariable String coachUUID, @PathVariable String personUUID){
        return domainPersonService.updateCoach(personUUID, coachUUID);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<?> removeOne(@PathVariable String uuid){
        return domainPersonService.removeOne(uuid);
    }

    @PutMapping("roles/{uuid}")
    public ResponseEntity<?> updateRolesForPerson(@PathVariable String uuid, @Valid @RequestBody RolesCollection roles){
        return domainPersonService.updateRoles(uuid, roles);
    }



}
