package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class CardOnlinePassEditDto extends CardEditDto {
    private String onlinePass;

    @Override
    public Card merge(Card card) {
        if (onlinePass != null && !onlinePass.isEmpty())
            card.setOnlinePass(onlinePass);
        return card;
    }
}
