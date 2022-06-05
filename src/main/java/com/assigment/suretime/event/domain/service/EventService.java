package com.assigment.suretime.event.domain.service;

import com.assigment.suretime.event.application.request.EventRequest;
import com.assigment.suretime.event.domain.Event;
import com.assigment.suretime.event.application.response.EventDto;
import com.assigment.suretime.event.domain.repository.EventRepository;
import com.assigment.suretime.event.application.controller.EventController;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.heat.domain.repository.HeatRepository;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class EventService extends GenericService<Event, EventDto, EventRepository> {

    final EventRepository eventRepository;
    final PersonRepository personRepository;
    final HeatRepository heatRepository;
    final GenericModelAssembler<Event> assembler =
            new GenericModelAssembler<>(Event.class, EventController.class);


    public EventService(EventRepository eventRepository,
                        PersonRepository personRepository,
                        HeatRepository heatRepository) {
        super(eventRepository, Event.class, new GenericModelAssembler<>(Event.class, EventController.class));
        this.eventRepository = eventRepository;
        this.personRepository = personRepository;
        this.heatRepository = heatRepository;
        
    }

    public ResponseEntity<?> createEvent(EventRequest request){
        var event = new Event(request.getName(), request.getStartTime());

        return ResponseEntity.ok(assembler.toModel(eventRepository.save(event)));
    }

    public ResponseEntity<?> updateName(String eventId, String newName){
        var event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.setName(newName);
        return ResponseEntity.ok(assembler.toModel(eventRepository.save(event)));
    }

    public ResponseEntity<?> addCompetitors(String eventId, Set<String> personsId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.getCompetitorsUuid().addAll(personsId);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    public ResponseEntity<?> removeCompetitors(String eventId, Set<String> personsId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.getCompetitorsUuid().removeAll(personsId);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    public ResponseEntity<?> addHeat(String eventId, String heatId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.getHeatsId().add(heatId);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(assembler.toModel(updated));

    }
    public ResponseEntity<?> deleteHeat(String eventId, String heatId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.getHeatsId().remove(heatId);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(assembler.toModel(updated));

    }

    public ResponseEntity<?> addCompetitor(String eventId, String competitorUuid) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.getCompetitorsUuid().add(competitorUuid);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    public ResponseEntity<?> deleteCompetitor(String eventId, String competitorUuid) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
        event.getCompetitorsUuid().remove(competitorUuid);
        Event updated = eventRepository.save(event);
        log.info("Updated heat: "+ event);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Override
    public Event fromDto(EventDto dto) {
        return new Event(dto.getName(),
                dto.getStartTime(), dto.getCompetitorsUuid(),
                dto.getHeatsId()
        );
    }
}
