package com.digipay.product.cardmanagmentservice.providers;

import com.digipay.product.cardmanagmentservice.dtos.TransitionDto;

public class BankServiceProvider {
    protected final String uri;
    public BankServiceProvider(String uri) {
        this.uri = uri;
    }

    public boolean doPayment(TransitionDto dto){
        DataPayload payload = DataPayload.builder()
                .source(dto.getSourceCardNumber())
                .dest(dto.getDistCardNumber())
                .cvv2(dto.getCvv2())
                .expDate(dto.getExpirationDate().toString())
                .pin(dto.getOnlinePass())
                .amount(String.valueOf(dto.getAmount()))
                .build();


        // TODO: 10/24/20 Use restTemple for call bank api
        return true;

    }

}
