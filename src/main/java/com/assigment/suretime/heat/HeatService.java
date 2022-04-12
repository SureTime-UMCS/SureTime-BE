package com.assigment.suretime.heat;

import com.assigment.suretime.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HeatService {

    HeatRepository heatRepository;
    HeatModelAssembler heatModelAssembler;
    public ResponseEntity<?> getOne(String id){
        Heat heat = heatRepository.findById(id).orElseThrow(()-> new NotFoundException("Heat", id));
        return ResponseEntity.ok(heatModelAssembler.toModel(heat));
    }

    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(heatModelAssembler.toCollectionModel(heatRepository.findAll()));
    }

    public ResponseEntity<?> addOne(Heat heat) {
        return ResponseEntity.ok(heatRepository.insert(heat));
    }

    public ResponseEntity<?> updateOne(Heat heat) {
        Heat toUpdateHeat = heatRepository.findById(heat.getId())
                .orElseThrow(()->new NotFoundException("Heat", heat.getId()));
        toUpdateHeat.update(heat);
        Heat updated = heatRepository.save(toUpdateHeat);
        return ResponseEntity.ok(heatModelAssembler.toModel(heat));
    }
}
