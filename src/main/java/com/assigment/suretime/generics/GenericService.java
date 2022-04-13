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
@NoArgsConstructor
public class GenericService<
        T extends MongoModel<T>,
        TRepository extends MongoRepository<T, String>> {

    TRepository repository;
    Class<T> tClass;
    GenericModelAssembler<T> modelAssembler;

    public GenericService(TRepository repository,
                          Class<T> tClass,
                          GenericModelAssembler<T> modelAssembler) {
        this.repository = repository;
        this.tClass = tClass;
        this.modelAssembler = modelAssembler;
    }

    public ResponseEntity<?> getOne(String id){
        T t = repository.findById(id).orElseThrow(()-> new NotFoundException("Heat", id));
        return ResponseEntity.ok(modelAssembler.toModel(t));
    }

    public CollectionModel<EntityModel<T>> getAll(){
        List<T > models = repository.findAll();
        return modelAssembler.toCollectionModel(models);
    }

    public ResponseEntity<?> addOne(T t) {
        return ResponseEntity.ok(repository.insert(t));
    }

    public ResponseEntity<?> updateOne(T t) {
        T toUpdate = repository.findById(t.getId())
                .orElseThrow(()->new NotFoundException(tClass.getSimpleName(), t.getId()));
        toUpdate.update(t);
        T updated = repository.save(toUpdate);
        return ResponseEntity.ok(modelAssembler.toModel(updated));
    }

    public ResponseEntity<?> removeOne(String id){
        repository.deleteById(id);
        return ResponseEntity.ok("");
    }



}
