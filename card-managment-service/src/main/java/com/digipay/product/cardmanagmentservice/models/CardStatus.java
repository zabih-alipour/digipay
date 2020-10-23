package com.digipay.product.cardmanagmentservice.models;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CardStatus {
    ACTIVE(1, "active", "فعال"),
    INACTIVE(2, "inactive", "غیرفعل"),
    SUSPEND(3, "suspend", "معلق"),
    ;

    private int id;
    private String title;
    private String code;

    CardStatus(int id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }
}
