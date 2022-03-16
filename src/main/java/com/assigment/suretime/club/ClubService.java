package com.assigment.suretime.club;


import com.assigment.suretime.NotFoundException;
import com.assigment.suretime.person.Person;
import com.assigment.suretime.person.PersonRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClubService
{
    private final ClubRepository clubRepository;
    private final PersonRepository personRepository;
    private final ClubModelAssembler clubModelAssembler;
    private static final Logger log = LoggerFactory.getLogger(ClubService.class);

    public EntityModel<Club> getClubById(String id){
        Club club = clubRepository.findById(id).orElseThrow(()-> new ClubNotFoundException(id));
        return clubModelAssembler.toModel(club);
    }

    public EntityModel<Club> getByName(String name){
        Club club = clubRepository.findByName(name).orElseThrow(()-> new ClubNotFoundException(name));
        return clubModelAssembler.toModel(club);
    }

    public List<EntityModel<Club>> getAll(){
        List<EntityModel<Club>> clubs = clubRepository.findAll().stream().map(clubModelAssembler::toModel).toList();
        return clubs;
    }

    public EntityModel<Club> addOne(Club newClub) {
        Optional<Club> club = clubRepository.findByName(newClub.getName());
        if(club.isEmpty()){
            return clubModelAssembler.toModel(clubRepository.insert(newClub));
        }else{
            return clubModelAssembler.toModel(club.get());
        }

    }

    public EntityModel<Club> updateOne(Club newClub, String name) {
        return clubRepository.findByName(name).map(c->{
            c.setName(newClub.getName());
            c.setAddress(newClub.getAddress());
            return clubModelAssembler.toModel(clubRepository.save(c));
        }).orElseGet(()->{
            Club club = new Club(newClub.getAddress(), newClub.getName());
            return clubModelAssembler.toModel(clubRepository.insert(club));
        });

    }

    public void deleteByName(String name) {
        clubRepository.deleteClubByName(name);
    }


    public EntityModel<Club> addPersonToClub(String clubName, String email) {
        Club club = clubRepository.findByName(clubName).orElseThrow(()-> new NotFoundException("Club",clubName));
        Person person = personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Person",email));
        club.setMembers(Collections.singletonList(person));
        return clubModelAssembler.toModel(club);

    }
}
