package com.digipay.product.cardmanagmentservice.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
public class TransitionDto {
    private String sourceCardNumber;
    private String onlinePass;
    private String cvv2;
    private String distCardNumber;
    private LocalDate expirationDate;
    private Long amount;

}
