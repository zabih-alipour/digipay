package com.digipay.product.cardmanagmentservice.providers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DataPayload {
    private String source;
    private String dest;
    private String cvv2;
    private String expDate;
    private String pin;
    private String amount;
}
