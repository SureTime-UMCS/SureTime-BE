package com.assigment.suretime.competitor;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/competitors")
@AllArgsConstructor
public class CompetitorController {

    private final CompetitorService competitorService;

    @GetMapping
    public List<Competitor> fetchAllCompetitors(){
        return competitorService.getAllCompetitors();
    }

    //@GetMapping("/{email}")
    //public Competitor fetchByEmail(@PathVariable String email){
    //
    //}


}
