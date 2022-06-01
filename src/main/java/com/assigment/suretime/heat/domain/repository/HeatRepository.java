package com.assigment.suretime.heat.domain.repository;

import com.assigment.suretime.heat.domain.Heat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatRepository extends MongoRepository<Heat, String> {

}
