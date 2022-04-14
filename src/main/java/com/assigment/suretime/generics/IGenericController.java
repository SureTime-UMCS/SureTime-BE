package com.assigment.suretime.generics;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface IGenericController<T,TDto> {

    ResponseEntity<?> one(String id);

    ResponseEntity<?> updateOne(TDto t);

    CollectionModel<?> all();

    ResponseEntity<?> deleteOne(@PathVariable String id);
}
