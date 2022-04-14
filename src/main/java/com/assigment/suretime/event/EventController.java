package com.assigment.suretime.event;

import com.assigment.suretime.generics.IGenericController;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping
    public ResponseEntity<?> updateOne(EventDto event) {
        return eventService.updateOne(event);
    }
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id){
        return eventService.deleteOne(id);
    }
}
