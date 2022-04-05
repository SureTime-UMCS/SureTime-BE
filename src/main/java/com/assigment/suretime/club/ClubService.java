package com.assigment.suretime.club;


import com.assigment.suretime.club.requestsModels.AddPersonsToClubModeratorModel;
import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.securityJwt.authenticationFacade.AuthenticationFacade;
import com.assigment.suretime.securityJwt.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ClubService
{
    protected final ClubRepository clubRepository;
    protected final PersonRepository personRepository;
    protected final ClubModelAssembler clubModelAssembler;
    private final AuthenticationFacade authenticationFacade;

    public ResponseEntity<EntityModel<Club>> getClubById(String id){
        Club club = clubRepository.findById(id).orElseThrow(()-> new NotFoundException("Club",id));
        return ResponseEntity.ok(clubModelAssembler.toModel(club));
    }

    public ResponseEntity<EntityModel<Club>> getByName(String name){
        Club club = clubRepository.findByName(name).orElseThrow(()-> new NotFoundException("Club", name));
        return ResponseEntity.ok(clubModelAssembler.toModel(club));
    }

    public ResponseEntity<CollectionModel<EntityModel<Club>>> getAll(){
        return ResponseEntity.ok(clubModelAssembler.toCollectionModel(clubRepository.findAll()));
    }

    public ResponseEntity<EntityModel<Club>> addOne(Club newClub) {
        Optional<Club> club = clubRepository.findByName(newClub.getName());
        if(club.isEmpty()){
            EntityModel<Club> responseEntity = clubModelAssembler.toModel(clubRepository.insert(newClub));
            return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.SEE_OTHER);
        }

    }
    public ResponseEntity<?> updateClub(Club newClub, String name) {
        //if other club has newClub.getName() will not allowed to make two clubs have same unique name.
        clubRepository.findByName(newClub.getName())
                .ifPresent(c->{
                    throw new AlreadyExistsException(newClub.getName());
                });


        return ResponseEntity.ok(clubRepository.findByName(name)
                .map(club -> {
                    club.updateNotNullFields(newClub);

                    clubRepository.save(club);
                    return clubModelAssembler.toModel(club);
                })
                .orElseThrow(()->new NotFoundException("Club", name)));
    }
    public ResponseEntity<?> deleteByName(String name) {
        clubRepository.deleteClubByName(name);
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?>addPersonToClub(String clubName, String email) {
        Club club = clubRepository.findByName(clubName).orElseThrow(()-> new NotFoundException("Club",clubName));
        UserDetailsImpl userDetails = authenticationFacade.getUserDetailsImpl();
        boolean isClubModerator = userDetails != null && club.getClubModerators().stream().anyMatch(person -> person.getEmail().equals(userDetails.getEmail()));
        if(!AuthenticationFacade.isAdmin() && !isClubModerator){
            return new ResponseEntity<>("Your have to be admin or club moderator to edit this resource", HttpStatus.FORBIDDEN);
        }

        Person person = personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Person",email));
        Set<Person> clubMembers = club.getMembers();
        clubMembers.add(person);
        club.setMembers(clubMembers);
        clubRepository.save(club);
        return ResponseEntity.ok(clubModelAssembler.toModel(club));

    }

    public ResponseEntity<?> removeOne(String name){
        clubRepository.findByName(name)
                .ifPresentOrElse(c-> {
                    log.info("Deleted club: "+ name);
                    clubRepository.delete(c);
                    }, ()->{log.info("Not deleted: "+name+" because do not exist already.");});
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> addModeratorsToClub(String clubName,
                                                                 AddPersonsToClubModeratorModel moderatorsEmail) {
        Club club = clubRepository.findByName(clubName).orElseThrow(()-> new NotFoundException("Club",clubName));

        UserDetailsImpl userDetails = authenticationFacade.getUserDetailsImpl();
        boolean isClubModerator = userDetails != null && club.getClubModerators().stream().anyMatch(person -> person.getEmail().equals(userDetails.getEmail()));
        if(!AuthenticationFacade.isAdmin() && !isClubModerator){
            return new ResponseEntity<>("Your have to be admin or club moderator to edit this resource", HttpStatus.FORBIDDEN);
        }

        List<Person> personList = moderatorsEmail.getPersonEmails()
                .stream().map(email -> personRepository
                        .findByEmail(email)
                        .orElseThrow(()-> new NotFoundException("Person", email))).toList();
        Set<Person> clubModerators = club.getClubModerators();
        clubModerators.addAll(new HashSet<>(personList));
        club.setClubModerators(clubModerators);
        clubRepository.save(club);
        return ResponseEntity.ok(clubModelAssembler.toModel(club));

    }
}
