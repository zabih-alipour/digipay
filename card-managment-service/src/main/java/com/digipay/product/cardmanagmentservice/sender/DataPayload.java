package com.digipay.product.cardmanagmentservice.sender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
class DataPayload implements Serializable {
    private String target;
    private String msg;

    public DataPayload(String target, String msg) {
        this.target = target;
        this.msg = msg;
    }

}