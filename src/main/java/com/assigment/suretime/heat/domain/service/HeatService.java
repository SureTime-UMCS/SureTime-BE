package com.assigment.suretime.heat.domain.service;

import com.assigment.suretime.event.domain.Event;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.heat.domain.repository.HeatRepository;
import com.assigment.suretime.heat.application.controller.HeatController;
import com.assigment.suretime.heat.domain.Heat;
import com.assigment.suretime.heat.application.response.HeatDto;
import com.assigment.suretime.person.domain.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class HeatService extends GenericService<Heat,HeatDto, HeatRepository> {

    final HeatRepository heatRepository;
    final GenericModelAssembler<Heat> assembler;
    final PersonRepository personRepository;

    public HeatService(HeatRepository heatRepository, PersonRepository personRepository) {
        super(heatRepository, Heat.class, new GenericModelAssembler<>(Heat.class, HeatController.class));
        this.heatRepository = heatRepository;
        this.personRepository = personRepository;
        this.assembler = new GenericModelAssembler<>(Heat.class, HeatController.class);
    }

    @Override
    public Heat fromDto(HeatDto dto) {
        Heat heat = new Heat();
        heat.setId(dto.getId());
        heat.setName(dto.getName());
        heat.setStartTime(dto.getStartTime());

        heat.setResults(heat.getResults());
        return heat;
    }

    public ResponseEntity<?> addCompetitors(String heatId, List<String> competitors) {
        if (competitors == null  || competitors.size()==0){
            return ResponseEntity.ok("");
        }
        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.getCompetitors().addAll(competitors);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(assembler.toModel(updated));
    }


    public ResponseEntity<?> addResults(String heatId, List<Pair<String, String>> resultList)
    {
        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        Map<String, String> oldAndNewResults = heat.getResults();
        resultList.forEach(emailResultPair -> {
            oldAndNewResults.put(emailResultPair.getFirst(), emailResultPair.getSecond());
        });
        heat.setResults(oldAndNewResults);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(assembler.toModel(updated));

    }

    public ResponseEntity<?> deleteCompetitors(String heatId, List<String> competitors) {
        if (competitors == null  || competitors.size()==0){
            return ResponseEntity.ok("");
        }
        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.getCompetitors().removeAll(competitors);

        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    public ResponseEntity<?> deleteResults(String heatId, List<Pair<String, String>> toDeleteResults) {
        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        var results = heat.getResults();
        toDeleteResults.forEach(emailResultPair -> {
            results.remove(emailResultPair.getFirst());
        });
        heat.setResults(results);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(assembler.toModel(updated));

    }

    public ResponseEntity<?> updateName(String heatId, String newName) {
        var heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Event.class.getSimpleName(), heatId));
        heat.setName(newName);
        Heat updated = heatRepository.save(heat);
        return ResponseEntity.ok(assembler.toModel(updated));
    }
}
