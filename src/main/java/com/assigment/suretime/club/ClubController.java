package com.assigment.suretime.club;

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

    @PostMapping()
    ResponseEntity<EntityModel<Club>> addOne(@RequestBody Club club){
        return service.addOne(club);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{name}")
    ResponseEntity<EntityModel<Club>> updateOne(@PathVariable String name, @RequestBody Club newClub){
        ResponseEntity<EntityModel<Club>> entityModel = service.updateOne(newClub, name);
        return entityModel;

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    ResponseEntity<?> deleteClub(@PathVariable String name){
        return service.deleteByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{name}/add_person_to_club")
    ResponseEntity<EntityModel<Club>> addPersonToClub(@PathVariable String name, @RequestBody String personEmail){
        return service.addPersonToClub(name, personEmail);
    }

}
