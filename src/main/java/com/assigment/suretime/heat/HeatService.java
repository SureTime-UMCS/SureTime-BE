package com.assigment.suretime.heat;

import com.assigment.suretime.event.Event;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.heat.models.Heat;
import com.assigment.suretime.heat.models.HeatDto;
import com.assigment.suretime.person.PersonRepository;
import com.assigment.suretime.person.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class HeatService extends GenericService<Heat,HeatDto, HeatRepository> {

    final HeatRepository heatRepository;
    final GenericModelAssembler<Heat> heatModelAssembler;
    final PersonRepository personRepository;

    public HeatService(HeatRepository heatRepository, PersonRepository personRepository) {
        super(heatRepository, Heat.class, new GenericModelAssembler<>(Heat.class, HeatController.class));
        this.heatRepository = heatRepository;
        this.personRepository = personRepository;
        this.heatModelAssembler = new GenericModelAssembler<>(Heat.class, HeatController.class);
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

    public ResponseEntity<?> updateCompetitors(String heatId, List<String> competitors) {
        if (competitors == null  || competitors.size()==0){
            return ResponseEntity.ok("");
        }

        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.setCompetitors(competitors);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(updated);
    }


    public ResponseEntity<?> updateResults(String heatId, List<Pair<String, String>> resultList)
    {
        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        Map<String, String> newResult = new HashMap<>();
        resultList.forEach(emailResultPair -> {
            newResult.put(emailResultPair.getFirst(), emailResultPair.getSecond());
        });
        heat.setResults(newResult);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<?> updateName(String heatId, String newName) {
        var heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Event.class.getSimpleName(), heatId));
        heat.setName(newName);
        return ResponseEntity.ok(heatRepository.save(heat));
    }

}
