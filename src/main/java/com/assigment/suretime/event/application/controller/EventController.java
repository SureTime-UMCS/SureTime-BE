package com.assigment.suretime.event.application.controller;

import com.assigment.suretime.event.application.request.EventRequest;
import com.assigment.suretime.event.domain.Event;
import com.assigment.suretime.event.application.response.EventDto;
import com.assigment.suretime.event.domain.service.EventService;
import com.assigment.suretime.generics.IGenericController;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
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
        return eventService.updateOne(id, event);
    }

    @PostMapping("")
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventRequest request){
        return eventService.createEvent(request);
    }

    @PostMapping("{id}/heat/{heat_id}")
    public ResponseEntity<?> addHeat(@PathVariable("id") String id,
                                     @PathVariable("heat_id") String heatId){
        return eventService.addHeat(id, heatId);
    }
    @DeleteMapping("{id}/heat/{heat_id}")
    public ResponseEntity<?> deleteHeat(@PathVariable("id") String id,
                                     @PathVariable("heat_id") String heatId){
        return eventService.deleteHeat(id, heatId);
    }

    @DeleteMapping("{id}/competitor/{uuid}")
    public ResponseEntity<?> deleteCompetitor(@PathVariable("id") String id,
                                        @PathVariable("uuid") String uuid){
        return eventService.deleteCompetitor(id, uuid);
    }

    @PostMapping("{id}/competitor/{uuid}")
    public ResponseEntity<?> addCompetitor(@PathVariable("id") String id,
                                              @PathVariable("uuid") String uuid){
        return eventService.addCompetitor(id, uuid);
    }


    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id){
        return eventService.deleteOne(id);
    }
}
