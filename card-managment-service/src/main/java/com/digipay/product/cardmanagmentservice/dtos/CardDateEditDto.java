package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public class CardDateEditDto extends CardEditDto {
    private LocalDate expirationDate;

    @Override
    public Card merge(Card card) {
        if (expirationDate != null)
            card.setExpirationDate(expirationDate);
        return card;
    }
}
