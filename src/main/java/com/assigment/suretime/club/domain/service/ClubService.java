package com.assigment.suretime.club.domain.service;

import com.assigment.suretime.club.application.request.AddPersonsToClubModel;
import com.assigment.suretime.club.application.request.ClubDTO;
import com.assigment.suretime.club.domain.Club;
import com.assigment.suretime.exceptions.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface ClubService {

    ResponseEntity<EntityModel<Club>> getByUUID(String name);

    ResponseEntity<CollectionModel<EntityModel<Club>>> getAll();

    ResponseEntity<EntityModel<Club>> addOne(ClubDTO newClub);

    ResponseEntity<?> updateClub(ClubDTO newClub, String uuid);

    ResponseEntity<?> deleteByUUID(String uuid);

    ResponseEntity<?> addPersonToClub(String clubName, AddPersonsToClubModel usersUUID);

    ResponseEntity<?> removeOne(String name);

    ResponseEntity<?> addModeratorsToClub(String clubName, AddPersonsToClubModel moderatorsUUID);

    Club getOrElseThrow(Optional<Club> clubRepository, NotFoundException clubName);
}