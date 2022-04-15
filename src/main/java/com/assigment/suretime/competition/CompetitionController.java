package com.assigment.suretime.competition;


import com.assigment.suretime.generics.IGenericController;
import com.assigment.suretime.generics.MongoModel;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/competitions")
@AllArgsConstructor
public class CompetitionController implements IGenericController<Competition, CompetitionDto> {

    CompetitionService service;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        return service.getOne(id);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id
            ,@RequestBody @Valid CompetitionDto dto) {
        return service.updateOne(id,dto);
    }

    @Override
    @GetMapping
    public CollectionModel<?> all() {
        return service.getAll();
    }


    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        return service.deleteOne(id);
    }
}
