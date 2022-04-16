package com.assigment.suretime.competition;


import com.assigment.suretime.competition.models.Competition;
import com.assigment.suretime.competition.models.CompetitionDto;
import com.assigment.suretime.generics.IGenericController;
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

    @PostMapping("{id}/add_competitor/{email}")
    public ResponseEntity<?> addCompetitor(@PathVariable("id") String id ,
                                           @PathVariable("email") String competitorEmail ){
        return service.addCompetitionCompetitor(id, competitorEmail);
    }
    @DeleteMapping("{id}/remove_competitor/{email}")
    public ResponseEntity<?> removeCompetitor(@PathVariable("id") String id ,
                                           @PathVariable("email") String email ){
        return service.removeCompetitionCompetitor(id, email);
    }
    @PostMapping("{id}/add_event/{event_id}")
    public ResponseEntity<?> addEvent(@PathVariable("id") String id
            , @PathVariable("event_id") String eventId){
        return service.addCompetitionEvent(id, eventId);
    }
    @DeleteMapping ("{id}/remove_event/{event_id}")
    public ResponseEntity<?> removeEvent(@PathVariable("id") String id,
                                         @PathVariable("event_id") String eventId){
        return service.removeCompetitionEvent(id, eventId);
    }

}
