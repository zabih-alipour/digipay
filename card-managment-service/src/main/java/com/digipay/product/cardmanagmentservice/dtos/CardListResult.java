package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardListResult extends PageImpl<Card> {

    public CardListResult() {
        super(new ArrayList<>());
    }

    public CardListResult(Page<Card> cards) {
        super(cards.getContent(), cards.getPageable(), cards.getTotalElements());
    }


}
