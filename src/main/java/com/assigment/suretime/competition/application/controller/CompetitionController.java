package com.assigment.suretime.competition.application.controller;


import com.assigment.suretime.competition.domain.service.CompetitionService;
import com.assigment.suretime.competition.domain.Competition;
import com.assigment.suretime.competition.application.response.CompetitionDto;
import com.assigment.suretime.generics.IGenericController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/competitions")
public class CompetitionController implements IGenericController<Competition, CompetitionDto> {

    final CompetitionService competitionService;

    public CompetitionController(CompetitionService service) {
        this.competitionService = service;
    }


    @Override
    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        return competitionService.getOne(id);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id
            ,@RequestBody @Valid CompetitionDto dto) {
        return competitionService.updateOne(id,dto);
    }

    @Override
    @GetMapping
    public CollectionModel<?> all() {
        return competitionService.getAll();
    }


    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        return competitionService.deleteOne(id);
    }

    @PostMapping("{id}/competitor/{email}")
    public ResponseEntity<?> addCompetitor(@PathVariable("id") String id ,
                                           @PathVariable("email") String competitorEmail ){
        return competitionService.addCompetitionCompetitor(id, competitorEmail);
    }
    @DeleteMapping("{id}/competitor/{email}")
    public ResponseEntity<?> removeCompetitor(@PathVariable("id") String id ,
                                           @PathVariable("email") String email ){
        return competitionService.removeCompetitionCompetitor(id, email);
    }
    @PostMapping("{id}/event/{event_id}")
    public ResponseEntity<?> addEvent(@PathVariable("id") String id
            , @PathVariable("event_id") String eventId){
        return competitionService.addCompetitionEvent(id, eventId);
    }
    @DeleteMapping ("{id}/event/{event_id}")
    public ResponseEntity<?> removeEvent(@PathVariable("id") String id,
                                         @PathVariable("event_id") String eventId){
        return competitionService.removeCompetitionEvent(id, eventId);
    }

}
