package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class CardOnlinePassEditDto extends EditDto<Card> {
    private String onlinePass;

    @Override
    public Optional<Card> merge(Card card) {
        if (onlinePass != null && !onlinePass.isEmpty())
            card.setOnlinePass(onlinePass);
        return Optional.ofNullable(card);
    }

}
