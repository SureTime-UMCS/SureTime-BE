package com.assigment.suretime.heat;

import com.assigment.suretime.heat.models.Heat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatRepository extends MongoRepository<Heat, String> {

}
