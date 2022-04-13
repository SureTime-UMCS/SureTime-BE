package com.assigment.suretime.heat;

import com.assigment.suretime.exceptions.NotFoundException;
import com.assigment.suretime.generics.GenericModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HeatService {

    HeatRepository heatRepository;
    GenericModelAssembler<Heat> heatModelAssembler;

    public HeatService(HeatRepository heatRepository) {
        this.heatRepository = heatRepository;
        this.heatModelAssembler = new GenericModelAssembler<>(Heat.class, HeatController.class);
    }

    public ResponseEntity<?> getOne(String id){
        Heat heat = heatRepository.findById(id).orElseThrow(()-> new NotFoundException("Heat", id));
        return ResponseEntity.ok(heatModelAssembler.toModel(heat));
    }

    public CollectionModel<?> getAll(){
        return heatModelAssembler.toCollectionModel(heatRepository.findAll());
    }

    public ResponseEntity<?> addOne(Heat heat) {
        return ResponseEntity.ok(heatRepository.insert(heat));
    }

    public ResponseEntity<?> updateOne(Heat heat) {
        Heat toUpdateHeat = heatRepository.findById(heat.getId())
                .orElseThrow(()->new NotFoundException("Heat", heat.getId()));
        toUpdateHeat.update(heat);
        Heat updated = heatRepository.save(toUpdateHeat);
        return ResponseEntity.ok(heatModelAssembler.toModel(updated));
    }
}
