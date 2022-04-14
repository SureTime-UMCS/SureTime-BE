package com.assigment.suretime.heat;


import com.assigment.suretime.generics.IGenericController;
import com.assigment.suretime.heat.models.AddCompetitorsRequest;
import com.assigment.suretime.heat.models.AddResultsRequest;
import com.assigment.suretime.heat.models.Heat;
import com.assigment.suretime.heat.models.HeatDto;
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

    @Override
    @PutMapping
    public ResponseEntity<?> updateOne(@RequestBody HeatDto t) {
        return heatService.updateOne(t);
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

    @PostMapping("add_competitors")
    public ResponseEntity<?> addCompetitor(
            @RequestBody @Valid AddCompetitorsRequest request) {
        return heatService.updateCompetitors(request.getHeatId(), request.getCompetitorsEmails());
    }

    @PostMapping("add_results")
    public ResponseEntity<?> addResults(
            @RequestBody @Valid AddResultsRequest request) {
        return heatService.updateResults(request.getHeatId(), request.getResults());
    }

}