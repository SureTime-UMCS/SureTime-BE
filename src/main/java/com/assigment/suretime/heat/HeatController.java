package com.assigment.suretime.heat;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/heats")
@AllArgsConstructor
public class HeatController {

    HeatService heatService;

    @GetMapping("{id}")
    public ResponseEntity<?> one(@PathVariable String id){
        return heatService.getOne(id);
    }
    @GetMapping
    public ResponseEntity<?> all(){
        return heatService.getAll();
    }


    @PostMapping
    public ResponseEntity<?> addOne(@RequestBody Heat heat){
        return heatService.addOne(heat);
    }

    @PutMapping
    public ResponseEntity<?> updateOne(@RequestBody Heat heat){
        return heatService.updateOne(heat);
    }
}
