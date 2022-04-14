package com.assigment.suretime.competition;

import com.assigment.suretime.event.Event;
import com.assigment.suretime.event.EventRepository;
import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompetitionService extends GenericService<Competition, CompetitionDto, CompetitionRepository> {

    final GenericModelAssembler<Competition> modelAssembler = new GenericModelAssembler<>(Competition.class, CompetitionController.class);
    final CompetitionRepository competitionRepository;
    final PersonRepository personRepository;
    final EventRepository eventRepository;

    public CompetitionService(CompetitionRepository competitionRepository,
                              PersonRepository personRepository,
                              EventRepository eventRepository) {
        this.competitionRepository = competitionRepository;
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<?> addOne(CompetitionDto competitionDto){
        Competition competition = fromDto(competitionDto);
        competitionRepository.findByName(competitionDto.getName())
                .ifPresent(c -> {
                    throw new AlreadyExistsException(competition.getName(),
                            "First delete "+competition.getName()+"Object");});
        log.info("inserted: "+ competition.getName());
        return ResponseEntity.ok(modelAssembler.toModel(competitionRepository.insert(competition)));
    }


    @Override
    public Competition fromDto(CompetitionDto competitionDto) {
        List<String> competitors = new ArrayList<>();
        competitionDto.getCompetitors().forEach(email -> personRepository.findByEmail(email).ifPresent(Person::getEmail));
        List<Event> events = competitionDto.getEventsId().stream()
                .map(eventId -> eventRepository.findById(eventId)
                        .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId))).toList();
        return Competition.builder()
                .competitors(competitors)
                .address(competitionDto.getAddress())
                .startTime(competitionDto.getStartTime())
                .endTime(competitionDto.getEndTime())
                .events(events)
                .name(competitionDto.getName())
                .build();
    }


    public ResponseEntity<?> deleteOne(String competitionName){
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

        competition.setCompetitors(competitors.stream().map(Person::getEmail).collect(Collectors.toList()));

        return new ResponseEntity<>(
                modelAssembler.toModel(competitionRepository.save(competition)),
                HttpStatus.CREATED);

    }


}
