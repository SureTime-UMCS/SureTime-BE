package com.assigment.suretime.heat.application.controller;


import com.assigment.suretime.event.application.request.EventRequest;
import com.assigment.suretime.generics.IGenericController;
import com.assigment.suretime.heat.application.request.*;
import com.assigment.suretime.heat.domain.service.HeatService;
import com.assigment.suretime.heat.application.response.HeatDto;
import com.assigment.suretime.heat.domain.Heat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/heats")
public class HeatController implements IGenericController<Heat, HeatDto> {

    @Autowired
    HeatService heatService;

    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        return heatService.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<?> createHeat(@RequestBody @Valid HeatRequest request){
        return heatService.createHeat(request);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id,
                                       @RequestBody @Valid HeatDto t) {
        return heatService.updateOne(id,t);
    }

    @GetMapping
    public CollectionModel<?> all() {
        return heatService.getAll();
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        return heatService.deleteOne(id);
    }


    @PostMapping("{id}/competitors")
    public ResponseEntity<?> addCompetitor(
            @RequestBody @Valid AddCompetitorsRequest request, @PathVariable String id) {
        return heatService.addCompetitors(id, request.getCompetitorsUUIDs());
    }

    @DeleteMapping("{id}/competitors")
    public ResponseEntity<?> deleteCompetitor(
            @RequestBody @Valid DeleteCompetitorsRequest request, @PathVariable String id) {
        return heatService.deleteCompetitors(id, request.getCompetitorsUUIDs());
    }

    @PostMapping("{id}/results")
    public ResponseEntity<?> addResults(
            @RequestBody @Valid AddResultsRequest request, @PathVariable String id) {
        return heatService.addResults(id, request.getResults());
    }

    @DeleteMapping("{id}/results")
    public ResponseEntity<?> deleteResults(
            @RequestBody @Valid DeleteResultsRequest request, @PathVariable String id) {
        return heatService.deleteResults(id, request.getResults());
    }

}