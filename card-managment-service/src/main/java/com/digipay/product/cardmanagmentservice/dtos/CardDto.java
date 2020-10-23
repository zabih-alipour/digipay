package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public class CardDto {
    private String number;
    private User user;
    private Long vcc2;
    private Long balance;
    private LocalDate expirationDate;
    private String onlinePass;

    public Card getEntity() {
        return Card.builder()
                .number(this.number)
                .user(user)
                .expirationDate(this.expirationDate)
                .onlinePass(this.onlinePass)
                .currentBalance(this.balance)
                .vcc2(this.vcc2)
                .build();
    }
}
