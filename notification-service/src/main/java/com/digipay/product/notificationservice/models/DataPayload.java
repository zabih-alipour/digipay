package com.digipay.product.notificationservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DataPayload {
    private String msg;
    private String target;

    public DataPayload() {
    }
}
