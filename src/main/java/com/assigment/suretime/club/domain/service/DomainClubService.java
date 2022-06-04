package com.assigment.suretime.club.domain.service;


import com.assigment.suretime.club.application.request.ClubDTO;
import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.club.domain.ClubModelAssembler;
import com.assigment.suretime.club.domain.repository.ClubRepository;
import com.assigment.suretime.club.application.request.AddPersonsToClubModel;
import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.domain.models.Person;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import com.assigment.suretime.securityJwt.domain.authenticationFacade.AuthenticationFacade;
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
public class DomainClubService implements ClubService {

    protected final ClubRepository clubRepository;
    protected final PersonRepository personRepository;
    protected final ClubModelAssembler clubModelAssembler;
    private final AuthenticationFacade authenticationFacade;

    public ResponseEntity<EntityModel<Club>> getByUUID(String uuid){
        Club club = getOrElseThrow(clubRepository.findByClubUUID(uuid), new NotFoundException("Club", uuid));
        return ResponseEntity.ok(clubModelAssembler.toModel(club));
    }

    public ResponseEntity<CollectionModel<EntityModel<Club>>> getAll(){
        return ResponseEntity.ok(clubModelAssembler.toCollectionModel(clubRepository.findAll()));
    }

    public ResponseEntity<EntityModel<Club>> addOne(ClubDTO newClub) {
        Optional<Club> club = clubRepository.findByName(newClub.getName());
        if(club.isEmpty()){
            EntityModel<Club> responseEntity = clubModelAssembler.toModel(clubRepository.insert(new Club(newClub)));
            return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.SEE_OTHER);
        }

    }

    public ResponseEntity<?> updateClub(ClubDTO newClub, String uuid) {
        Club modifiedClub = getOrElseThrow(clubRepository.findByClubUUID(uuid), new NotFoundException("Club", uuid));

        boolean isClubModerator = authenticationFacade.isClubModerator(modifiedClub);
        if(!AuthenticationFacade.isAdmin() && !isClubModerator){
            return new ResponseEntity<>("Your have to be admin or club moderator to edit this resource", HttpStatus.FORBIDDEN);
        }

        clubRepository.findByName(newClub.getName())
                .ifPresent(c->{
                    throw new AlreadyExistsException(newClub.getName());
                });

        modifiedClub.setMembers(newClub.getMembers());
        modifiedClub.setClubModerators(newClub.getClubModerators());
        modifiedClub.setName(newClub.getName());
        modifiedClub.setAddress(newClub.getAddress());

        clubRepository.save(modifiedClub);
        return ResponseEntity.ok(clubModelAssembler.toModel(modifiedClub));

    }
    public ResponseEntity<?> deleteByUUID(String uuid) {
        clubRepository.deleteClubByClubUUID(uuid);
        return ResponseEntity.ok("");
    }


    public ResponseEntity<?> addPersonToClub(String clubUUID, AddPersonsToClubModel usersUUID) {
        Club club = getOrElseThrow(clubRepository.findByClubUUID(clubUUID), new NotFoundException("Club", clubUUID));
        boolean isClubModerator = authenticationFacade.isClubModerator(club);
        if(!AuthenticationFacade.isAdmin() && !isClubModerator){
            return new ResponseEntity<>("Your have to be admin or club moderator to edit this resource", HttpStatus.FORBIDDEN);
        }

        List<Person> personList = usersUUID.getPersonUUID()
                .stream().map(uuid -> personRepository
                        .findByUserUUID(uuid)
                        .orElseThrow(()-> new NotFoundException("Person", uuid))).toList();
        Set<String> clubMembers = club.getMembers();
        for(var person: personList){
            clubMembers.add(person.getUserUUID());
        }

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

    public ResponseEntity<?> addModeratorsToClub(String clubUUID, AddPersonsToClubModel moderatorsUUID) {
        Club club = getOrElseThrow(clubRepository.findByClubUUID(clubUUID), new NotFoundException("Club", clubUUID));
        boolean isClubModerator = authenticationFacade.isClubModerator(club);

        if(!AuthenticationFacade.isAdmin() && !isClubModerator){
            return new ResponseEntity<>("Your have to be admin or club moderator to edit this resource", HttpStatus.FORBIDDEN);
        }

        List<Person> personList = moderatorsUUID.getPersonUUID()
                .stream().map(uuid -> personRepository
                        .findByUserUUID(uuid)
                        .orElseThrow(()-> new NotFoundException("Person", uuid))).toList();
        Set<String> clubModerators = club.getClubModerators();
        for(var person: personList){
            clubModerators.add(person.getUserUUID());
        }

        club.setClubModerators(clubModerators);
        clubRepository.save(club);
        return ResponseEntity.ok(clubModelAssembler.toModel(club));

    }

    public Club getOrElseThrow(Optional<Club> clubRepository, NotFoundException clubName) {
        return clubRepository.orElseThrow(() -> clubName);
    }
}
