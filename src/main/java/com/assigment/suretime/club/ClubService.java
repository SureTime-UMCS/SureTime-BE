package com.assigment.suretime.club;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClubService
{
    private final ClubRepository repository;
    private final ClubModelAssembler clubModelAssembler;
    private static final Logger log = LoggerFactory.getLogger(ClubService.class);

    public EntityModel<Club> getClubById(String id){
        Club club = repository.findById(id).orElseThrow(()-> new ClubNotFoundException(id));
        return clubModelAssembler.toModel(club);
    }

    public EntityModel<Club> getClubByName(String name){
        Club club = repository.findByName(name).orElseThrow(()-> new ClubNotFoundException(name));
        return clubModelAssembler.toModel(club);
    }

    public List<EntityModel<Club>> getAllClubs(){
        List<EntityModel<Club>> clubs = repository.findAll().stream().map(clubModelAssembler::toModel).toList();
        return clubs;
    }

    public EntityModel<Club> addClub(Club newClub) {
        Optional<Club> club = repository.findByName(newClub.getName());
        if(club.isEmpty()){
            return clubModelAssembler.toModel(repository.insert(newClub));
        }else{
            return clubModelAssembler.toModel(club.get());
        }

    }

    public EntityModel<Club> replaceClub(Club newClub, String name) {
        return repository.findByName(name).map(c->{
            c.setName(newClub.getName());
            c.setAddress(newClub.getAddress());
            return clubModelAssembler.toModel(repository.save(c));
        }).orElseGet(()->{
            Club club = new Club(newClub.getAddress(), newClub.getName());
            return clubModelAssembler.toModel(repository.insert(club));
        });

    }

    public void deleteByName(String name) {
        repository.deleteClubByName(name);
    }
}
