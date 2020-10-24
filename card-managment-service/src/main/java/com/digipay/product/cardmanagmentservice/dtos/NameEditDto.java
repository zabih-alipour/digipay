package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class NameEditDto extends EditDto<Card> {
    private String name;

    public NameEditDto() {
    }

    @Override
    public Optional<Card> merge(Card card) {
        card.setName(this.name);
        return Optional.of(card);
    }
}
