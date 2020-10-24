package com.digipay.product.cardmanagmentservice.controllers;

import com.digipay.product.cardmanagmentservice.dtos.CardDto;
import com.digipay.product.cardmanagmentservice.dtos.NameEditDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card_management/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cardService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<Card>> get(@RequestBody(required = false) SearchDto searchDto) {
        return ResponseEntity.ok(cardService.find(searchDto));
    }

    @PostMapping
    public ResponseEntity<Card> add(@RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.add(cardDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws Exception {
        cardService.delete(id);
        return ResponseEntity.ok("Card deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> edit(@PathVariable("id") Long id, @RequestBody NameEditDto cardDto) throws Exception {
        return ResponseEntity.ok(cardService.edit(id, cardDto));
    }
}
