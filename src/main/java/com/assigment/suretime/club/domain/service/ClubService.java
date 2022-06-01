package com.assigment.suretime.club.domain.service;

import com.assigment.suretime.club.application.request.AddPersonsToClubModeratorModel;
import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.exceptions.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface ClubService {

    ResponseEntity<EntityModel<Club>> getClubById(String id);

    ResponseEntity<EntityModel<Club>> getByName(String name);

    ResponseEntity<CollectionModel<EntityModel<Club>>> getAll();

    ResponseEntity<EntityModel<Club>> addOne(Club newClub);

    ResponseEntity<?> updateClub(Club newClub, String name);

    ResponseEntity<?> deleteByName(String name);

    ResponseEntity<?> addPersonToClub(String clubName, String email);

    ResponseEntity<?> removeOne(String name);

    ResponseEntity<?> addModeratorsToClub(String clubName, AddPersonsToClubModeratorModel moderatorsEmail);

    Club getOrElseThrow(Optional<Club> clubRepository, NotFoundException clubName);
}