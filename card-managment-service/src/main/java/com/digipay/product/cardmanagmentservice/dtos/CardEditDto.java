package com.digipay.product.cardmanagmentservice.dtos;

import com.digipay.product.cardmanagmentservice.models.Card;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public abstract class CardEditDto {

    public abstract Card merge(Card card);

}
