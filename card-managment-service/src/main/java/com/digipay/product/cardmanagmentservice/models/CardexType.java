package com.digipay.product.cardmanagmentservice.models;

import lombok.Getter;

@Getter
public enum CardexType {

    INITIAL(1, "INITIAL", "موجودی اولیه"),
    TRANSFER(2, "TRANSFER", "انتقال"),
    WITHDRAW(3, "WITHDRAW", "برداشت"),
    DEPOSIT(4, "DEPOSIT", "واریز"),
    ;

    private int id;
    private String title;
    private String code;

    CardexType(int id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }
}