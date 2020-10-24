package com.digipay.product.cardmanagmentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private String cardName;
    private Long cntSuccess;
    private Long cntFail;
    private Long amount;

    public ReportDto(String cardName, Long cntSuccess, Long cntFail, Long amount) {
        this.cardName = cardName;
        this.cntSuccess = cntSuccess;
        this.cntFail = cntFail;
        this.amount = amount;
    }
}
