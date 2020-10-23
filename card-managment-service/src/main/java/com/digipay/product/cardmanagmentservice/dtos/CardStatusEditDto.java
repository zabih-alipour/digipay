package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.CardStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class CardStatusEditDto extends EditDto<Card> {
    private CardStatus cardStatus;

    @Override
    public Optional<Card> merge(Card card) {
        if (cardStatus != null)
            card.setCardStatus(cardStatus);
        return Optional.ofNullable(card);
    }
}
