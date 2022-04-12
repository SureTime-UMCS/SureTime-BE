package com.assigment.suretime.competition;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompetitionRepository extends MongoRepository<Competition, String> {
    Optional<Competition> findByName(String name);
    void deleteByName(String name);
}
