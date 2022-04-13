package com.assigment.suretime.generics;


import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.heat.Heat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class GenericService<
        T extends MongoModel,
        TRepository extends MongoRepository<T, String>,
        TController extends IGenericController> {

    TRepository repository;
    TController controller;
    Class<T> tClass;
    GenericModelAssembler<T> modelAssembler;



    public ResponseEntity<?> getOne(String id){
        T t = repository.findById(id).orElseThrow(()-> new NotFoundException("Heat", id));
        return ResponseEntity.ok(modelAssembler.toModel(t));
    }

    public CollectionModel<EntityModel<T>> getAll(){
        List<T > models = repository.findAll();
        return modelAssembler.toCollectionModel(models);
    }

}
