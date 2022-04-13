package com.assigment.suretime.event;

import com.assigment.suretime.generics.IGenericController;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/events")
@AllArgsConstructor
public class EventController implements IGenericController {



    @Override
    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        return null;
    }

    @GetMapping
    @Override
    public CollectionModel<?> all() {
        return null;
    }
}
