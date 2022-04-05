package com.assigment.suretime.club;


import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.person.models.Person;
import com.assigment.suretime.person.PersonRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClubService
{
    private final ClubRepository clubRepository;
    private final PersonRepository personRepository;
    private final ClubModelAssembler clubModelAssembler;
    private static final Logger log = LoggerFactory.getLogger(ClubService.class);

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
    public ResponseEntity<EntityModel<Club>> updateOne(Club newClub, String name) {
        clubRepository.findByName(newClub.getName())
                .ifPresent(c->{
                    throw new AlreadyExistsException(newClub.getName());
                });
        return ResponseEntity.ok(clubRepository.findByName(name)
                .map(club -> {
                    club.update(newClub);
                    clubRepository.save(club);
                    return clubModelAssembler.toModel(club);
                })
                .orElseThrow(()->new NotFoundException("Club", name)));
    }
    public ResponseEntity<?> deleteByName(String name) {
        clubRepository.deleteClubByName(name);
        return ResponseEntity.ok("");
    }

    public ResponseEntity<EntityModel<Club>>addPersonToClub(String clubName, String email) {
        Club club = clubRepository.findByName(clubName).orElseThrow(()-> new NotFoundException("Club",clubName));
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
}
