package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.CardStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public class CardStatusEditDto extends CardEditDto {
    private CardStatus cardStatus;

    @Override
    public Card merge(Card card) {
        if (cardStatus != null)
            card.setCardStatus(cardStatus);
        return card;
    }
}
