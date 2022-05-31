package com.assigment.suretime.heat;


import com.assigment.suretime.generics.IGenericController;
import com.assigment.suretime.heat.models.*;
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
        return heatService.addCompetitors(request.getHeatId(), request.getCompetitorsEmails());
    }

    @DeleteMapping("{id}/competitors")
    public ResponseEntity<?> deleteCompetitor(
            @RequestBody @Valid DeleteCompetitorsRequest request, @PathVariable String id) {
        return heatService.deleteCompetitors(request.getHeatId(), request.getCompetitorsEmails());
    }

    @PostMapping("{id}/results")
    public ResponseEntity<?> addResults(
            @RequestBody @Valid AddResultsRequest request, @PathVariable String id) {
        return heatService.addResults(request.getHeatId(), request.getResults());
    }

    @DeleteMapping("{id}/results")
    public ResponseEntity<?> deleteResults(
            @RequestBody @Valid DeleteResultsRequest request, @PathVariable String id) {
        return heatService.deleteResults(request.getHeatId(), request.getResults());
    }

}