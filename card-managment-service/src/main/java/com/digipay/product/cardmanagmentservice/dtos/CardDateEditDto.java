package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class CardDateEditDto extends EditDto<Card> {
    private LocalDate expirationDate;

    @Override
    public Optional<Card> merge(Card card) {
        if (expirationDate != null)
            card.setExpirationDate(expirationDate);
        return Optional.ofNullable(card);
    }
}
