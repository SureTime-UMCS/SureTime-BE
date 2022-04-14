package com.assigment.suretime.event;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.heat.models.Heat;
import com.assigment.suretime.heat.HeatRepository;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EventService extends GenericService<Event,EventRepository> {

    final EventRepository eventRepository;
    final PersonRepository personRepository;
    final HeatRepository heatRepository;
    final GenericModelAssembler<Event> eventModelAssembler =
            new GenericModelAssembler<>(Event.class, EventController.class);


    public EventService(EventRepository eventRepository,
                        PersonRepository personRepository,
                        HeatRepository heatRepository) {
        super(eventRepository, Event.class, new GenericModelAssembler<>(Event.class, EventController.class));
        this.eventRepository = eventRepository;
        this.personRepository = personRepository;
        this.heatRepository = heatRepository;
        
    }
    public ResponseEntity<?> updateName(String eventId, String newName){
        var event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.setName(newName);
        return ResponseEntity.ok(eventRepository.save(event));
    }

    public ResponseEntity<?> addCompetitors(String eventId, Set<String> personsId){
        List<Person> competitors = StreamSupport
                .stream(personRepository.findAllById(personsId).spliterator(), false).toList();

        return addCompetitors(eventId, competitors);
    }

    public ResponseEntity<?> addCompetitors(String eventId, List<Person> competitors){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.setCompetitors(competitors);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<?> addHeats(String eventId, Set<String> heatsId){
        List<Heat> heats = StreamSupport
                .stream(heatRepository.findAllById(heatsId).spliterator(), false).toList();
        return addHeats(eventId, heats);
    }

    public ResponseEntity<?> addHeats(String eventId, List<Heat> heats){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.setHeats(heats);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(updated);
    }
}
