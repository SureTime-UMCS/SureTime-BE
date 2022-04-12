package com.assigment.suretime.competition;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.IController;
import com.assigment.suretime.generics.ModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService implements IController {

    CompetitionRepository competitionRepository;
    ModelAssembler<Competition> modelAssembler;

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
        this.modelAssembler = new ModelAssembler<>(Competition.class, CompetitionController.class);
    }

    @Override
    public ResponseEntity<?> one(String id) {
        return competitionRepository.findById(id)
                .map(competition -> {return modelAssembler.toModel(competition);})
                .map(ResponseEntity::ok)
                .orElseThrow(()->new NotFoundException(Competition.class.getSimpleName(), id));
    }

    @Override
    public CollectionModel<?> all() {
        return modelAssembler.toCollectionModel(competitionRepository.findAll());
    }
}
