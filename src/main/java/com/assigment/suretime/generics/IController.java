package com.assigment.suretime.generics;

import com.assigment.suretime.competition.Competition;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface IController {

    public ResponseEntity<?> one(String id);
    public CollectionModel<?> all();

}
