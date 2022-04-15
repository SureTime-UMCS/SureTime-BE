package com.assigment.suretime.generics;


import com.assigment.suretime.competition.Competition;
import com.assigment.suretime.competition.CompetitionDto;
import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public abstract class GenericService<
        T extends MongoModel,
        TDto extends MongoDto,
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

    public abstract T fromDto(TDto dto);

    public ResponseEntity<?> getOne(String id) {
        T t = repository.findById(id).orElseThrow(() -> new NotFoundException("Heat", id));
        return ResponseEntity.ok(modelAssembler.toModel(t));
    }

    public CollectionModel<EntityModel<T>> getAll() {
        List<T> models = repository.findAll();
        return modelAssembler.toCollectionModel(models);
    }

    public ResponseEntity<?> addOne(TDto dto) {
        T t = fromDto(dto);
        if (repository.findById(t.getId()).isPresent()) {
            throw new AlreadyExistsException(tClass.getSimpleName(), t.toString());
        }
        return ResponseEntity.ok(repository.insert(t));
    }

    public ResponseEntity<?> updateOne(String id,TDto dto) {
        T t = fromDto(dto);
        T toUpdate = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(tClass.getSimpleName(), t.getId()));
        toUpdate.updateNotNullFields(t);
        T updated = repository.save(toUpdate);
        return ResponseEntity.ok(modelAssembler.toModel(updated));
    }

    public ResponseEntity<?> deleteOne(String id) {
        repository.deleteById(id);
        return ResponseEntity.ok("");
    }
}
