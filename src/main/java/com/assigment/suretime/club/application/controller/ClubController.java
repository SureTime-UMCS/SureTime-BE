package com.assigment.suretime.club.application.controller;

import com.assigment.suretime.club.application.request.ClubDTO;
import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.club.domain.service.ClubService;
import com.assigment.suretime.club.application.request.AddPersonsToClubModel;
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

    @GetMapping("/{clubUUID}")
    public ResponseEntity<EntityModel<Club>> getOne(@PathVariable String clubUUID){
        return service.getByUUID(clubUUID);
    }

    @PostMapping()
    ResponseEntity<?> addOne(@RequestBody ClubDTO club){
        return service.addOne(club);
    }

    @PutMapping("/{clubUUID}")
    ResponseEntity<?> updateClub(@PathVariable String clubUUID, @RequestBody ClubDTO newClub){
        ResponseEntity<?> entityModel = service.updateClub(newClub, clubUUID);
        return entityModel;

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{clubUUID}")
    ResponseEntity<?> deleteClub(@PathVariable String clubUUID){
        return service.deleteByUUID(clubUUID);
    }

    @PostMapping("/{clubUUID}/person")
    ResponseEntity<?> addPersonToClub(@PathVariable String clubUUID, @RequestBody @Valid AddPersonsToClubModel persons){
        return service.addPersonToClub(clubUUID, persons);
    }

    @PostMapping("{clubUUID}/moderators")
    ResponseEntity<?> addModeratorsToClub(
            @PathVariable String clubUUID,
            @RequestBody @Valid AddPersonsToClubModel persons){
        return service.addModeratorsToClub(clubUUID, persons);
    }

}
