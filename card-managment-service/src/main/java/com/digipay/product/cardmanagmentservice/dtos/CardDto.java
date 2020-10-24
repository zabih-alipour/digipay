package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import com.digipay.product.cardmanagmentservice.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Setter
@Getter
@SuperBuilder
public class CardDto extends ParentDto<Card> {
    private String number;
    private String name;
    private User user;


    public CardDto() {
    }

    @Override
    public Optional<Card> entityInstance() {
        return Optional.ofNullable(
                Card.builder()
                        .number(this.number)
                        .name(Optional.ofNullable(this.name).orElse(this.number))
                        .user(user)
                        .build());
    }
}
