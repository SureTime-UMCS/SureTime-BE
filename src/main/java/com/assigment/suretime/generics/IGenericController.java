package com.assigment.suretime.generics;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface IGenericController {

    public ResponseEntity<?> one(String id);
    public CollectionModel<?> all();

}
