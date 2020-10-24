package com.digipay.product.cardmanagmentservice.controllers;

import com.digipay.product.cardmanagmentservice.dtos.ReportDto;
import com.digipay.product.cardmanagmentservice.dtos.SearchDto;
import com.digipay.product.cardmanagmentservice.models.Cardex;
import com.digipay.product.cardmanagmentservice.setvices.CardexService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class CardexController {

    private final CardexService cardexService;

    public CardexController(CardexService cardexService) {
        this.cardexService = cardexService;
    }

    @GetMapping
    public ResponseEntity<Page<Cardex>> get(@RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(cardexService.find(searchDto));
    }

    @GetMapping("/{from}/{to}")
    public ResponseEntity<List<ReportDto>> get(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return ResponseEntity.ok(cardexService.report(from, to));
    }
}
