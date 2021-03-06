package com.assigment.suretime.competition.application.controller;


import com.assigment.suretime.competition.application.request.CompetitionRequest;
import com.assigment.suretime.competition.domain.service.CompetitionService;
import com.assigment.suretime.competition.domain.Competition;
import com.assigment.suretime.competition.application.response.CompetitionDto;
import com.assigment.suretime.event.application.request.EventRequest;
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

    @PostMapping("")
    public ResponseEntity<?> createCompetition(@RequestBody @Valid CompetitionRequest request){
        return competitionService.createCompetition(request);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        return competitionService.deleteOne(id);
    }

    @PostMapping("{id}/competitor/{uuid}")
    public ResponseEntity<?> addCompetitor(@PathVariable("id") String id ,
                                           @PathVariable("uuid") String uuid ){
        return competitionService.addCompetitionCompetitor(id, uuid);
    }
    @DeleteMapping("{id}/competitor/{email}")
    public ResponseEntity<?> removeCompetitor(@PathVariable("id") String id ,
                                           @PathVariable("uuid") String uuid ){
        return competitionService.removeCompetitionCompetitor(id, uuid);
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
