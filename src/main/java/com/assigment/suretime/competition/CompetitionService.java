package com.assigment.suretime.competition;

import com.assigment.suretime.event.Event;
import com.assigment.suretime.event.EventRepository;
import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.IController;
import com.assigment.suretime.generics.ModelAssembler;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CompetitionService implements IController {

    final ModelAssembler<Competition> modelAssembler = new ModelAssembler<>(Competition.class, CompetitionController.class);
    final CompetitionRepository competitionRepository;
    final PersonRepository personRepository;
    final EventRepository eventRepository;

    @Override
    public ResponseEntity<?> one(String id) {
        return competitionRepository.findById(id)
                .map(modelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(()->new NotFoundException(Competition.class.getSimpleName(), id));
    }

    @Override
    public CollectionModel<?> all() {
        return modelAssembler.toCollectionModel(competitionRepository.findAll());
    }

    public ResponseEntity<?> addOne(CompetitionDto competitionDto){
        Competition competition = competitionFromDto(competitionDto);
        competitionRepository.findByName(competitionDto.getName())
                .ifPresent(c -> {
                    throw new AlreadyExistsException(competition.getName(),
                            "First delete "+competition.getName()+"Object");});
        log.info("inserted: "+ competition.getName());
        return ResponseEntity.ok(modelAssembler.toModel(competitionRepository.insert(competition)));
    }

    public ResponseEntity<?> removeOne(String competitionName){
        competitionRepository.deleteByName(competitionName);
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> updateCompetitionEvents(String competitionId, List<String> eventsId){
        List<Event> events = eventsId.stream()
                .map(id -> eventRepository.findById(id)
                        .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), id)))
                .toList();

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(()->new NotFoundException(Competition.class.getSimpleName(), competitionId));

        competition.setEvents(events);

        return new ResponseEntity<>(modelAssembler.toModel(
                competitionRepository.save(competition)),
                HttpStatus.CREATED);

    }
    public ResponseEntity<?> updateCompetitionCompetitors(String competitionId, List<String> emails){
        List<Person> competitors = emails.stream()
                .map(personEmail -> personRepository.findById(personEmail)
                        .orElseThrow(()->new NotFoundException(Person.class.getSimpleName(), personEmail)))
                .toList();

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(()->new NotFoundException(Competition.class.getSimpleName(), competitionId));

        competition.setCompetitors(competitors.stream()
                .collect(Collectors.toMap(Person::getEmail, person->person)));

        return new ResponseEntity<>(
                modelAssembler.toModel(competitionRepository.save(competition)),
                HttpStatus.CREATED);

    }


    private Competition competitionFromDto(CompetitionDto competitionDto) {
        Map<String, Person> competitors = new HashMap<>();
        competitionDto.getCompetitors().forEach(email -> {
            personRepository.findByEmail(email).ifPresent(person -> competitors.put(email, person));
        });
        return Competition.builder()
                .competitors(competitors)
                .address(competitionDto.getAddress())
                .startTime(competitionDto.getStartTime())
                .endTime(competitionDto.getEndTime())
                .events(competitionDto.getEvents())
                .name(competitionDto.getName())
                .build();
    }
}
