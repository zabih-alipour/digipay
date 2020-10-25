package com.digipay.product.cardmanagmentservice.controllers;

import com.digipay.product.cardmanagmentservice.dtos.TransitionDto;
import com.digipay.product.cardmanagmentservice.setvices.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private CardService cardService;

    public TransferController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card-to-card")
    public ResponseEntity<Boolean> add(@RequestBody TransitionDto transitionDto) {
        return ResponseEntity.ok(cardService.transfer(transitionDto));
    }

}
