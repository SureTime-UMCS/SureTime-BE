package com.assigment.suretime.person;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.models.PersonDTO;
import com.assigment.suretime.person.models.RolesCollection;
import com.assigment.suretime.securityJwt.authenticationFacade.AuthenticationFacade;
import com.assigment.suretime.securityJwt.authenticationFacade.IAuthenticationFacade;
import com.assigment.suretime.securityJwt.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
            return personService.updateOrCreatePerson(personDTO);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody PersonDTO person){
            return personService.updateOrCreatePerson(person);
    }

    @DeleteMapping("{email}")
    public ResponseEntity<?> removeOne(@PathVariable String email){
        return personService.removeOne(email);
    }

    @PutMapping("update_roles/{email}")
    public ResponseEntity<?> updateRolesForPerson(@PathVariable String email, @Valid @RequestBody RolesCollection roles){
        return personService.updateRoles(email, roles);
    }



}
