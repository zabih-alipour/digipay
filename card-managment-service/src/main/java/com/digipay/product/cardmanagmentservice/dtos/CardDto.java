package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class CardDto extends ParentDto<Card> {
    private String number;
    private User user;
    private Long vcc2;
    private LocalDate expirationDate;
    private String onlinePass;

    @Override
    public Optional<Card> getEntity() {
        return Optional.ofNullable(
                Card.builder()
                        .number(this.number)
                        .user(user)
                        .expirationDate(this.expirationDate)
                        .onlinePass(this.onlinePass)
                        .vcc2(this.vcc2)
                        .build());
    }
}
