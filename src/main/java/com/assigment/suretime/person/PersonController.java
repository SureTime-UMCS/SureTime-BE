package com.assigment.suretime.person;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.securityJwt.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/me")
    public ResponseEntity<EntityModel<Person>> me(Authentication authentication){
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return personService.getByEmail(userDetails.getEmail());
        }catch (Exception e){
            throw new NotFoundException("Person", "?");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<EntityModel<Person>> addOne(@RequestBody Person person){
        return personService.addOne(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{personEmail}")
    public ResponseEntity<EntityModel<Person>> updateCoach(@PathVariable String personEmail, @RequestBody String coachEmail){
        return personService.updateCoach(personEmail, coachEmail);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{email}")
    public ResponseEntity<?> removeOne(@PathVariable String email){
        return personService.removeOne(email);
    }

}
