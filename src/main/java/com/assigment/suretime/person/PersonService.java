package com.assigment.suretime.person;


import com.assigment.suretime.club.Club;
import com.assigment.suretime.club.ClubRepository;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.models.PersonDTO;
import com.assigment.suretime.person.models.RolesCollection;
import com.assigment.suretime.securityJwt.models.ERole;
import com.assigment.suretime.securityJwt.models.Role;
import com.assigment.suretime.securityJwt.repository.RoleRepository;
import com.assigment.suretime.securityJwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.assigment.suretime.securityJwt.authenticationFacade.AuthenticationFacade.isAdmin;
import static com.assigment.suretime.securityJwt.authenticationFacade.AuthenticationFacade.isModifingOwnData;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

    protected final PersonRepository personRepository;
    protected final PersonModelAssembler personAssembler;
    protected final ClubRepository clubRepository;
    protected final RoleRepository roleRepository;
    protected final UserRepository userRepository;

    private Person createPersonFromDTO(PersonDTO personDTO){
        var coachOptional = personRepository.findByEmail(personDTO.getCoachEmail());
        var clubOptional = clubRepository.findByName(personDTO.getClubName());
        Person coach = null;
        Club club = null;
        if(coachOptional.isPresent())
            coach = coachOptional.get();
        if(clubOptional.isPresent())
            club = clubOptional.get();

        Gender gender = null;
        if (personDTO.getGender()!=null)
            gender = Gender.valueOf(personDTO.getGender().toString().toUpperCase(Locale.ROOT));

        Person person = new Person(personDTO.getFirstName(), personDTO.getSecondName(),
                personDTO.getEmail(), gender, club, coach);
        return person;
    }
    public CollectionModel<EntityModel<Person>> all() {
        log.info("Returning all persons");
        return personAssembler.toCollectionModel(personRepository.findAll());
    }

    public ResponseEntity<EntityModel<Person>> getByEmail(String email) {
        log.info("Getting person:<"+email+">");
        return personRepository.findByEmail(email)
                .map(personAssembler::toModel).map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Person", email));

    }

    public ResponseEntity<?> removeOne(String email){
        personRepository.findByEmail(email)
                .ifPresentOrElse(p-> {
                    log.info("Deleted: "+ email);
                    personRepository.delete(p)
                    ;}, ()->{log.info("Not deleted: "+email+" because do not exist already.");});
        return ResponseEntity.ok("");
    }

    public ResponseEntity<EntityModel<Person>> updateOrCreate(PersonDTO personDTO) {
        return updatePerson(personDTO);
    }
    public ResponseEntity<EntityModel<Person>> updateOrCreate(Person person) {
        PersonDTO personDTO = createDTOFromPerson(person);
        return updatePerson(personDTO);
    }

    private PersonDTO createDTOFromPerson(Person person) {
        return new PersonDTO(person);
    }

    public ResponseEntity<?> updateOrCreate(PersonDTO personDTO, Authentication auth ) {
        Person person = createPersonFromDTO(personDTO);
        boolean isAdmin = isAdmin(auth);
        boolean isModifingOwnData = isModifingOwnData(person, auth);
        if( !isAdmin && !isModifingOwnData){
            return new ResponseEntity<>("It's possible to edit only own data.", HttpStatus.UNAUTHORIZED);
        }
        return updatePerson(personDTO);
    }



    private ResponseEntity<EntityModel<Person>> updatePerson(PersonDTO personDTO) {
        //if person is found then return response with 203 status (See other)
        //else create new person.
        return personRepository.findByEmail(personDTO.getEmail()).
                map(p -> {
                    log.info("Updating: ."+personDTO.getEmail() +" Person already exist");
                    Person person = createPersonFromDTO(personDTO);
                    p.update(person);
                    personRepository.save(p);
                    return new ResponseEntity<>(personAssembler.toModel(p), HttpStatus.SEE_OTHER);
                })
                .orElseGet(() ->
                {
                    log.info("Creating new person: "+ personDTO.getEmail());
                    Person fromDTO = createPersonFromDTO(personDTO);
                    Person newPerson = personRepository.save(new Person(fromDTO));
                    EntityModel<Person> entityModel = personAssembler.toModel(newPerson);
                    return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
                });
    }

    @Deprecated
    ResponseEntity<?> updateCoach(String personEmail, String coachEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isAdmin(authentication) && !isModifingOwnData(personEmail, authentication)){
            return new ResponseEntity<>("You are not allowed to modify thihs content", HttpStatus.FORBIDDEN);
        }
        var coach = personRepository.findByEmail(coachEmail).
                orElseThrow(() -> new NotFoundException("Person", coachEmail));
        var person = personRepository.findByEmail(personEmail).
                orElseThrow(() -> new NotFoundException("Person", personEmail));

        person.setCoach(coach);
        var updatedPerson = personRepository.save(person);
        log.info("Added coach"+coachEmail+ "to person"+ personEmail);
        return ResponseEntity.ok(personAssembler.toModel(updatedPerson));


    }

    public ResponseEntity<?> updateRoles(String email, RolesCollection newRoles) {
        var user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User", email));
        Map<ERole,Role> rolesMap = new HashMap<>();
        for (String roleStr: newRoles.getRoles()) {
            ERole eRole = ERole.valueOf(roleStr);
            Role role = roleRepository.findByName(eRole)
                    .orElseThrow(()-> new NotFoundException("Role", eRole.toString()));
            rolesMap.put(eRole, role);
        }
        if(!rolesMap.containsKey(ERole.ROLE_USER)){
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new NotFoundException("Role", ERole.ROLE_USER.toString()));
            rolesMap.put(ERole.ROLE_USER, role);
        }
        user.setRoles(new HashSet<>(rolesMap.values()));
        userRepository.save(user);
        return ResponseEntity.ok("");
    }
}
