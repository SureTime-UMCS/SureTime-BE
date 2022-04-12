package com.assigment.suretime.competition;


import com.assigment.suretime.generics.IController;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/competitions")
@AllArgsConstructor
public class CompetitionController implements IController {
    @Override
    public ResponseEntity<?> one(String id) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<Competition>> all() {
        return null;
    }
}
