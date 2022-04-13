package com.assigment.suretime.heat;

import com.assigment.suretime.event.Event;
import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericModelAssembler;
import com.assigment.suretime.generics.GenericService;
import com.assigment.suretime.person.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class HeatService extends GenericService<Heat,HeatRepository> {

    final HeatRepository heatRepository;
    final GenericModelAssembler<Heat> heatModelAssembler;

    public HeatService(HeatRepository heatRepository) {
        super(heatRepository, Heat.class, new GenericModelAssembler<>(Heat.class, HeatController.class));
        this.heatRepository = heatRepository;
        this.heatModelAssembler = new GenericModelAssembler<>(Heat.class, HeatController.class);
    }

    public ResponseEntity<?> addCompetitors(String heatId, List<Person> competitors){
        Heat heat = heatRepository.findById(heatId)
                .orElseThrow(()->new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.setCompetitors(competitors);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: "+ heat);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<?> addResults(String heatId, Map<Person, Float> results){
        Heat heat = heatRepository.findById(heatId)
                .orElseThrow(()->new NotFoundException(Heat.class.getSimpleName(), heatId));
        heat.setResults(results);
        Heat updated = heatRepository.save(heat);
        log.info("Updated heat: "+ heat);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<?> updateName(String heatId, String newName){
        var heat = heatRepository.findById(heatId)
                .orElseThrow(()->new NotFoundException(Event.class.getSimpleName(), heatId));
        heat.setName(newName);
        return ResponseEntity.ok(heatRepository.save(heat));
    }

}
