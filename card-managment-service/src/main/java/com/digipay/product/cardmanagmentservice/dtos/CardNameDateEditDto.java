package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public class CardNameDateEditDto extends CardEditDto {
    private String ownerName;
    private LocalDate expirationDate;

    @Override
    public Card merge(Card card) {
        if (ownerName != null && !ownerName.isEmpty())
            card.setOwnerName(ownerName);
        if (expirationDate != null)
            card.setExpirationDate(expirationDate);
        return card;
    }
}
