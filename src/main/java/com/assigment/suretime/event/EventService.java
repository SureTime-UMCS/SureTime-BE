package com.assigment.suretime.event;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.heat.Heat;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService extends GenericService<Event,EventRepository, EventController> {

    final EventRepository eventRepository;
    final GenericModelAssembler<Event> eventModelAssembler =
            new GenericModelAssembler<>(Event.class, EventController.class);




    public ResponseEntity<?> getOne(String id){
        return super.getOne(id);
    }

    @Override
    public CollectionModel<EntityModel<Event>> getAll() {
        return super.getAll();
    }

    public ResponseEntity<?> addOne(Event event) {
        return ResponseEntity.ok(eventRepository.insert(event));
    }

    public ResponseEntity<?> updateOne(Event event) {
        Event toUpdateHeat = eventRepository.findById(event.getId())
                .orElseThrow(()->new NotFoundException("Heat", event.getId()));
        toUpdateHeat.update(event);
        Event updated = eventRepository.save(toUpdateHeat);
        return ResponseEntity.ok(eventModelAssembler.toModel(updated));
    }
}
