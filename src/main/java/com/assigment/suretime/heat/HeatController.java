package com.assigment.suretime.heat;


import com.assigment.suretime.generics.IGenericController;
import com.assigment.suretime.heat.models.AddCompetitorsRequest;
import com.assigment.suretime.heat.models.AddResultsRequest;
import com.assigment.suretime.heat.models.Heat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/heats")
public class HeatController implements IGenericController {

    @Autowired
    HeatService heatService;

    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id) {
        return heatService.getOne(id);
    }

    @GetMapping
    public CollectionModel<?> all() {
        return heatService.getAll();
    }


    @PostMapping
    public ResponseEntity<?> addOne(@RequestBody Heat heat) {
        return heatService.addOne(heat);
    }

    @PutMapping
    public ResponseEntity<?> updateOne(@RequestBody Heat heat) {
        return heatService.updateOne(heat);
    }


    @PostMapping("add_competitors")
    public ResponseEntity<?> addCompetitor(
            @RequestBody @Valid AddCompetitorsRequest request) {
        return heatService.addCompetitors(request.getHeatId(), request.getCompetitorsEmails());
    }

    @PostMapping("add_results")
    public ResponseEntity<?> addResults(
            @RequestBody @Valid AddResultsRequest request) {
        return heatService.addResults(request.getHeatId(), request.getResults());
    }

    @PutMapping("update_name/{heatId}/{new_name}")
    public ResponseEntity<?> updateName(@PathVariable String heatId,@PathVariable String new_name) {
        return heatService.updateName(heatId, new_name);
    }
}