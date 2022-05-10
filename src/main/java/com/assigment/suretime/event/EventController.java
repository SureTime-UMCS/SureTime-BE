package com.assigment.suretime.event;

import com.assigment.suretime.generics.IGenericController;
import com.assigment.suretime.securityJwt.authenticationFacade.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/events")
@AllArgsConstructor
public class EventController implements IGenericController<Event, EventDto> {

    EventService eventService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        return eventService.getOne(id);
    }

    @GetMapping
    @Override
    public CollectionModel<?> all() {
        return eventService.getAll();
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id,
                                       @RequestBody @Valid EventDto event) {
        if(!AuthenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to create this content", HttpStatus.FORBIDDEN);
        }
        return eventService.updateOne(id, event);
    }

    @PostMapping("{id}/add_heat/{heat_id}")
    public ResponseEntity<?> addHeat(@PathVariable("id") String id,
                                     @PathVariable("heat_id") String heatId){
        if(!AuthenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to create this content", HttpStatus.FORBIDDEN);
        }
        return eventService.addHeat(id, heatId);
    }
    @DeleteMapping("{id}/delete_heat/{heat_id}")
    public ResponseEntity<?> deleteHeat(@PathVariable("id") String id,
                                     @PathVariable("heat_id") String heatId){
        if(!AuthenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to create this content", HttpStatus.FORBIDDEN);
        }
        return eventService.deleteHeat(id, heatId);
    }

    @DeleteMapping("{id}/delete_competitor/{email}")
    public ResponseEntity<?> deleteCompetitor(@PathVariable("id") String id,
                                        @PathVariable("email") String email){
        if(!AuthenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to create this content", HttpStatus.FORBIDDEN);
        }
        return eventService.deleteCompetitor(id, email);
    }

    @PostMapping("{id}/add_competitor/{email}")
    public ResponseEntity<?> addCompetitor(@PathVariable("id") String id,
                                              @PathVariable("email") String email){
        if(!AuthenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to create this content", HttpStatus.FORBIDDEN);
        }
        return eventService.addCompetitor(id, email);
    }


    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id){
        if(!AuthenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to create this content", HttpStatus.FORBIDDEN);
        }
        return eventService.deleteOne(id);
    }
}
