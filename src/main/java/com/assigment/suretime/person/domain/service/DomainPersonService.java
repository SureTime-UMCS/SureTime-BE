package com.assigment.suretime.person.domain.service;

import com.assigment.suretime.club.domain.repository.ClubRepository;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.application.response.PersonDTO;
import com.assigment.suretime.person.domain.PersonModelAssembler;
import com.assigment.suretime.person.domain.models.Gender;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.models.RolesCollection;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import com.assigment.suretime.securityJwt.domain.authenticationFacade.AuthenticationFacade;
import com.assigment.suretime.securityJwt.domain.models.ERole;
import com.assigment.suretime.securityJwt.domain.models.Role;
import com.assigment.suretime.securityJwt.domain.repository.RoleRepository;
import com.assigment.suretime.securityJwt.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class DomainPersonService implements PersonService {

    protected final PersonRepository personRepository;
    protected final PersonModelAssembler personAssembler;
    protected final ClubRepository clubRepository;
    protected final RoleRepository roleRepository;
    protected final UserRepository userRepository;
    protected final AuthenticationFacade authenticationFacade;

    private Person createPersonFromDTO(PersonDTO personDTO){
        var coachOptional = personRepository.findByUserUUID(personDTO.getCoachUUID());
        var clubOptional = clubRepository.findByClubUUID(personDTO.getClubUUID());
        String coach = "";
        String club = "";
        if(coachOptional.isPresent())
            coach = coachOptional.get().getCoachUUID();
        if(clubOptional.isPresent())
            club = clubOptional.get().getClubUUID();

        Gender gender = null;
        if (personDTO.getGender()!=null)
            gender = Gender.valueOf(personDTO.getGender().toString().toUpperCase(Locale.ROOT));

        return new Person(personDTO.getFirstName(), personDTO.getSecondName(),
                personDTO.getEmail(), gender, club, coach);

    }
    public CollectionModel<EntityModel<Person>> all() {
        log.info("Returning all persons");
        return personAssembler.toCollectionModel(personRepository.findAll());
    }

    public ResponseEntity<?> getByUUID(String uuid) {
        log.info("Getting person:<"+uuid+">");
        return personRepository.findByUserUUID(uuid)
                .map(personAssembler::toModel).map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Person", uuid));

    }

    public ResponseEntity<?> removeOne(String uuid){
        if(!AuthenticationFacade.isAdmin()){
            return new ResponseEntity<>("You are not allowed to modify this content", HttpStatus.FORBIDDEN);
        }
        personRepository.findByUserUUID(uuid)
                .ifPresentOrElse(p-> {
                    log.info("Deleted: "+ uuid + " from person repository");
                    personRepository.delete(p)
                    ;}, ()->{log.info("Not deleted: "+uuid+" because do not exist already.");});

        userRepository.findByUserUUID(uuid)
                .ifPresentOrElse(u-> {
                    log.info("Deleted: "+ uuid + " from user repository");
                    userRepository.delete(u)
                    ;}, ()->{log.info("Not deleted: "+uuid+" because do not exist already.");});

        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> updateOrCreate(Person person){
        return updateOrCreate(new PersonDTO(person));
    }
    public ResponseEntity<?> updateOrCreate(PersonDTO personDTO) {
        if(!AuthenticationFacade.isAdmin() && !AuthenticationFacade.isModifingOwnData(personDTO.getEmail())) {
            return new ResponseEntity<>("You are not allowed to modify thihs content", HttpStatus.FORBIDDEN);
        }
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
    public ResponseEntity<?> updateCoach(String personUUID, String coachUUID) {
        if(!AuthenticationFacade.isAdmin() && !AuthenticationFacade.isModifingOwnData(personUUID)){
            return new ResponseEntity<>("You are not allowed to modify thihs content", HttpStatus.FORBIDDEN);
        }

        if(personUUID.equals(coachUUID)){
            return new ResponseEntity<>("You can't be your own coach", HttpStatus.FORBIDDEN);
        }

        var coach = personRepository.findByUserUUID(coachUUID).
                orElseThrow(() -> new NotFoundException("Person", coachUUID));
        var person = personRepository.findByUserUUID(personUUID).
                orElseThrow(() -> new NotFoundException("Person", personUUID));

        person.setCoachUUID(coach.getUserUUID());
        var updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(personAssembler.toModel(updatedPerson));


    }
    //Only for Admin. 
    //If Club Admin want to add Club Admin role. Needs to use endpoint in clubs.
    
    public ResponseEntity<?> updateRoles(String uuid, RolesCollection newRoles) {
        if(!AuthenticationFacade.isAdmin()){
            return new ResponseEntity<>("You are not allowed to modify thihs content", HttpStatus.FORBIDDEN);
        }
        var user = userRepository.findByUserUUID(uuid).orElseThrow(()-> new NotFoundException("User", uuid));
        Set<Role> roles = new HashSet<>();
        //Prepare rolesObjects
        for (String roleStr: newRoles.getRoles()) {
            ERole eRole = ERole.valueOf(roleStr);
            Role role = roleRepository.findByName(eRole)
                    .orElseGet(()->roleRepository.insert(new Role(eRole)));
            roles.add(role);
        }
        
        //Assert user has basic user role.
        Role role = roleRepository.findByName(ERole.ROLE_BASIC_USER)
                .orElseGet(()->roleRepository.insert(new Role(ERole.ROLE_BASIC_USER)));
        roles.add(role);
        
        
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("");
        


    }
}
