package com.digipay.product.cardmanagmentservice.models;

import lombok.Getter;

@Getter
public enum TransferStatus {
    SUCCESS(1, "SUCCESS", "موفق"),
    FAIL(2, "FAIL", "نا موفق"),
    ;

    private int id;
    private String title;
    private String code;

    TransferStatus(int id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }
}