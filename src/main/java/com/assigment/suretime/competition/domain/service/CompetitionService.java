package com.assigment.suretime.competition.domain.service;

import com.assigment.suretime.competition.application.controller.CompetitionController;
import com.assigment.suretime.competition.application.request.CompetitionRequest;
import com.assigment.suretime.competition.domain.Competition;
import com.assigment.suretime.competition.application.response.CompetitionDto;
import com.assigment.suretime.competition.domain.repository.CompetitionRepository;
import com.assigment.suretime.event.domain.Event;
import com.assigment.suretime.event.domain.repository.EventRepository;
import com.assigment.suretime.exceptions.AlreadyExistsException;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import com.assigment.suretime.person.domain.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

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
        super(competitionRepository, Competition.class, new GenericModelAssembler<>(Competition.class, CompetitionController.class));
        this.competitionRepository = competitionRepository;
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<?> createCompetition(CompetitionRequest request) {
        Competition comp = new Competition();
        comp.setName(request.getName());
        comp.setAddress(request.getAddress());
        comp.setStartTime(request.getStartTime());
        comp.setEndTime(request.getEndTime());

        return ResponseEntity.ok(modelAssembler.toModel(competitionRepository.save(comp)));
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

    public ResponseEntity<?> deleteOne(String id){
        competitionRepository.deleteById(id);
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> updateCompetitionEvents(String competitionId, List<String> eventsId){
        eventsId.forEach(this::eventExistElseThrowNotFoundException);

        Competition competition = getCompetitionElseThrowNotFoundException(competitionId);

        competition.setEventsId(new HashSet<>(eventsId));

        return new ResponseEntity<>(modelAssembler.toModel(
                competitionRepository.save(competition)),
                HttpStatus.CREATED);

    }

    public ResponseEntity<?> addCompetitionEvent(String competitionId, String eventId){
        eventExistElseThrowNotFoundException(eventId);

        Competition competition = getCompetitionElseThrowNotFoundException(competitionId);

        competition.getEventsId().add(eventId);

        return new ResponseEntity<>(modelAssembler.toModel(
                competitionRepository.save(competition)),
                HttpStatus.OK);

    }

    public ResponseEntity<?> deleteCompetitionEvent(String competitionId, String eventId){
        eventExistElseThrowNotFoundException(eventId);

        Competition competition = getCompetitionElseThrowNotFoundException(competitionId);
        competition.getEventsId().remove(eventId);

        return new ResponseEntity<>(modelAssembler.toModel(
                competitionRepository.save(competition)),
                HttpStatus.OK);

    }



    public ResponseEntity<?> updateCompetitionCompetitors(String competitionId, List<String> emails){
        personsExistsElseThrowNotFoundException(emails);

        Competition competition = getCompetitionElseThrowNotFoundException(competitionId);

        competition.setCompetitors(new HashSet<>(emails));

        return new ResponseEntity<>(
                modelAssembler.toModel(competitionRepository.save(competition)),
                HttpStatus.OK);

    }


    public ResponseEntity<?> addCompetitionCompetitor(String competitionId, String uuid){
        //personsExistsElseThrowNotFoundException(List.of(email));
        Competition competition = getCompetitionElseThrowNotFoundException(competitionId);
        competition.getCompetitors().add(uuid);

        return new ResponseEntity<>(
                modelAssembler.toModel(competitionRepository.save(competition)),
                HttpStatus.OK);

    }

    public ResponseEntity<?> removeCompetitionEvent(String id, String eventId) {
        eventExistElseThrowNotFoundException(eventId);
        Competition competition = getCompetitionElseThrowNotFoundException(id);
        competition.getEventsId().remove(eventId);
        Competition updated = competitionRepository.save(competition);
        return ResponseEntity.ok(modelAssembler.toModel(updated));

    }

    public ResponseEntity<?> removeCompetitionCompetitor(String competitionId, String uuid){

        Competition competition = getCompetitionElseThrowNotFoundException(competitionId);

        competition.getCompetitors().remove(uuid);

        return new ResponseEntity<>(
                modelAssembler.toModel(competitionRepository.save(competition)),
                HttpStatus.OK);

    }

    private void personsExistsElseThrowNotFoundException(List<String> emails) {
        emails.forEach(personEmail -> personRepository.findById(personEmail)
                        .orElseThrow(() -> new NotFoundException(Person.class.getSimpleName(), personEmail)));
    }

    @Override
    public Competition fromDto(@NotNull CompetitionDto competitionDto) {
        //competitionDto.getCompetitors().forEach(this::personsExistElseThrowNotFoundException);
        //competitionDto.getEventsId().forEach(this::eventExistElseThrowNotFoundException);

        return new Competition(competitionDto.getId(),
                competitionDto.getName(),
                competitionDto.getAddress(),
                competitionDto.getEventsId(),
                competitionDto.getCompetitors(),
                competitionDto.getStartTime(),
                competitionDto.getEndTime());

    }

    private Competition getCompetitionElseThrowNotFoundException(String competitionId) {
        return competitionRepository.findById(competitionId)
                .orElseThrow(()->new NotFoundException(Competition.class.getSimpleName(), competitionId));
    }

    private void eventExistElseThrowNotFoundException(String eventId) {
        eventRepository.findById(eventId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), eventId));
    }
}
