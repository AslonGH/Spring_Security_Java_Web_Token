package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.entity.Outcome;
import uz.pdp.rest_api_jwt.payload.OutcomeDto;
import uz.pdp.rest_api_jwt.payload.Result;
import uz.pdp.rest_api_jwt.service.OutcomeService;

import java.util.List;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    @Autowired
    OutcomeService outComeService;


    @PostMapping
    public HttpEntity<?>addOutcome(@RequestBody OutcomeDto outcomeDto){
        Result result = outComeService.addOutcome(outcomeDto);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping
    public HttpEntity<?>getOutcome(){
        List<Outcome> outcomeList = outComeService.getOutcome();
        return ResponseEntity.ok(outcomeList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?>getOutcomeById(@PathVariable Integer id){
        Outcome outcomeById = outComeService.getOutcomeById(id);
        return  ResponseEntity.status(outcomeById !=null?HttpStatus.OK:HttpStatus.CONFLICT).body(outcomeById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?>editOutcome(@RequestBody OutcomeDto outcomeDto, @PathVariable Integer id){
        Result result = outComeService.editOutcomeById(outcomeDto, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteOutcome(@PathVariable Integer id){
        Result result = outComeService.deleteOutcome(id);
        return ResponseEntity.ok(result);
    }





}
