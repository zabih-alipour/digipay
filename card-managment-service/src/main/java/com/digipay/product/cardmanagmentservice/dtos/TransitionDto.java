package com.digipay.product.cardmanagmentservice.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class TransitionDto {
    private String sourceCardNumber;
    private String onlinePass;
    private String cvv2;
    private String distCardNumber;
    private String expirationDate;
    private Long amount;

}
