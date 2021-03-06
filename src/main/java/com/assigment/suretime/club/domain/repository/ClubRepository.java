package com.assigment.suretime.club.domain.repository;

import com.assigment.suretime.club.domain.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClubRepository extends MongoRepository<Club, String> {

    Optional<Club> findByName(String name);
    Optional<Club> findByClubUUID(String uuid);

    void deleteClubByClubUUID(String uuid);


}
