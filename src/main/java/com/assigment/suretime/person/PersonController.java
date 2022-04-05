package com.assigment.suretime.person;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.models.PersonDTO;
import com.assigment.suretime.person.models.RolesCollection;
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
    public ResponseEntity<EntityModel<Person>> one(@PathVariable String email){
        return personService.getByEmail(email);
    }

    @GetMapping("/me")
    public ResponseEntity<EntityModel<Person>> me(){
        try{
            Authentication auth = authenticationFacade.getAuthentication();
            UserDetailsImpl userDetails = ((UserDetailsImpl) auth.getPrincipal());
            return personService.getByEmail(userDetails.getEmail());
        }catch (Exception e){
            throw new NotFoundException("Exception", e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addOne(@RequestBody PersonDTO personDTO) {
            return personService.updateOrCreate(personDTO, authenticationFacade.getAuthentication());
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody PersonDTO person){
            return personService.updateOrCreate(person, authenticationFacade.getAuthentication());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{email}")
    public ResponseEntity<?> removeOne(@PathVariable String email){
        return personService.removeOne(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update_roles/{email}")
    public ResponseEntity<?> updateRolesForPerson(@PathVariable String email, @Valid @RequestBody RolesCollection roles){
        return personService.updateRoles(email, roles);
    }



}
