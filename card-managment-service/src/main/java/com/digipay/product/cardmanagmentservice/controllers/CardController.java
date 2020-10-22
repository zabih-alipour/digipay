package com.digipay.product.cardmanagmentservice.controllers;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<Card>> list(){
        return ResponseEntity.ok(cardService.list());
    }
}
