package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.exceptions.InvalidInputException;
import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public class CardBalanceEditDto extends CardEditDto {
    private Long currentBalance;
    private Operation operation;

    public enum Operation {
        ADD, SUBTRACT
    }

    @Override
    public Card merge(Card card) {
        if (currentBalance != null) {
            if (operation == Operation.ADD)
                card.setCurrentBalance(card.getCurrentBalance() + currentBalance);
            else if (operation == Operation.SUBTRACT)
                card.setCurrentBalance(card.getCurrentBalance() - currentBalance);
            else throw new InvalidInputException("عملگر");
        }
        return card;
    }
}
