package com.assigment.suretime.club;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClubRepository extends MongoRepository<Club, String> {

    Optional<Club> findByName(String name);

    void deleteClubByName(String name);
}
