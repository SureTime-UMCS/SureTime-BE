package com.assigment.suretime.club;

import com.assigment.suretime.club.requestsModels.AddPersonsToClubModeratorModel;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/clubs")
public class ClubController {

    private final ClubService service;

    @GetMapping("")
    public ResponseEntity<CollectionModel<EntityModel<Club>>> all(){
        return service.getAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel<Club>> getOne(@PathVariable String name){
        return service.getByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Club>> getOneById(@PathVariable String id){
        return service.getClubById(id);
    }


    @PostMapping()
    ResponseEntity<?> addOne(@RequestBody Club club){
        return service.addOne(club);
    }

    @PutMapping("/{name}")
    ResponseEntity<?> updateClub(@PathVariable String name, @RequestBody Club newClub){
        ResponseEntity<?> entityModel = service.updateClub(newClub, name);
        return entityModel;

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    ResponseEntity<?> deleteClub(@PathVariable String name){
        return service.deleteByName(name);
    }

    @PostMapping("/{name}/person")
    ResponseEntity<?> addPersonToClub(@PathVariable String name, @RequestBody String personEmail){
        return service.addPersonToClub(name, personEmail);
    }

    @PostMapping("{clubName}/moderators")
    ResponseEntity<?> addModeratorsToClub(
            @PathVariable String clubName,
            @RequestBody @Valid AddPersonsToClubModeratorModel persons){
        return service.addModeratorsToClub(clubName, persons);
    }

}
