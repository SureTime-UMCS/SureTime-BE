package com.assigment.suretime.competitor;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompetitorRepository extends MongoRepository<Competitor, String> {

    Optional<Competitor> findCompetitorByEmail(String email);
}
