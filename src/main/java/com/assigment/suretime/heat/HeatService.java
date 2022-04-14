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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        Map<Person, Float> results = new HashMap<>();
        dto.getResults().forEach((email, result) ->{
            var person = personRepository.findByEmail(email)
                    .orElseThrow(()->
                            new NotFoundException(Person.class.getSimpleName(), email));
            results.put(person, result);
        });
        heat.setResults(results);
        return heat;
    }

    public ResponseEntity<?> addCompetitors(String heatId, List<?> competitors) {
        if (competitors == null  || competitors.size()==0){
            return ResponseEntity.ok("");
        }
        if(competitors.get(0) instanceof String)
        {
            competitors = competitors.stream()
                    .map(email -> personRepository.findByEmail((String) email)
                            .orElseThrow(() ->
                                    new NotFoundException(Person.class.getSimpleName(),
                                            (String) email))).toList();
        }


        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.setCompetitors((List<Person>) competitors);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: " + heat);
        return ResponseEntity.ok(updated);
    }


    public ResponseEntity<?> addResults(String heatId, Map<String, Float> resultMap)
    {
        Map<Person, Float> results = new HashMap<>();
        resultMap.forEach((email, result) -> {
            var person = personRepository.findByEmail(email).orElseThrow(()->new NotFoundException(Person.class.getSimpleName(), email));
            results.put(person, result);
        });

        Heat heat = heatRepository.findById(heatId).orElseThrow(() -> new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.setResults(results);
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
