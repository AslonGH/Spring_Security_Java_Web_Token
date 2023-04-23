package uz.pdp.rest_api_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.entity.Income;
import uz.pdp.rest_api_jwt.payload.IncomeDto;
import uz.pdp.rest_api_jwt.payload.Result;
import uz.pdp.rest_api_jwt.service.IncomeService;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;


    @PostMapping
    public HttpEntity<?>addIncome(@RequestBody IncomeDto incomeDto){
        Result result = incomeService.addIncome(incomeDto);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping
    public HttpEntity<?>getIncomes(){
        List<Income> incomeList = incomeService.getIncomes();
        return ResponseEntity.ok(incomeList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?>getIncomesById(@PathVariable Integer id){
        Income incomeById = incomeService.getIncomeById(id);
        return  ResponseEntity.status(incomeById !=null? HttpStatus.OK:HttpStatus.CONFLICT).body(incomeById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?>editIncomes(@RequestBody IncomeDto incomeDto, @PathVariable Integer id){
        Result result = incomeService.editIncomeById(incomeDto, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteIncome(@PathVariable Integer id){
        Result result = incomeService.deleteIncome(id);
        return ResponseEntity.ok(result);
    }



}
