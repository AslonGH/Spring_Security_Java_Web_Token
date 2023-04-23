package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.entity.Card;
import uz.pdp.rest_api_jwt.payload.Result;
import uz.pdp.rest_api_jwt.service.CardService;


@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;


    @PostMapping
    public HttpEntity<?>addCard(@RequestBody Card card){
        Result result = cardService.addCard(card);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/{id}")
    public HttpEntity<?>getCardById(@PathVariable Integer id){
        Card cardById = cardService.getCardById(id);
        return  ResponseEntity.status(cardById !=null? HttpStatus.OK:HttpStatus.CONFLICT).body(cardById);
    }}


/*
    @GetMapping
    public HttpEntity<?>getCards(){
        List<Card> cardes = cardService.getCards();
        return ResponseEntity.ok(cardes);
    }
    @PutMapping("/{id}")
    public HttpEntity<?>editCard(@RequestBody Card card, @PathVariable Integer id){
        Result result = cardService.editCardById(card, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteCard(@PathVariable Integer id){
        Result result = cardService.deleteCard(id);
        return ResponseEntity.ok(result);
    }

*/



