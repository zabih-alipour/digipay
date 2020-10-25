package com.digipay.product.cardmanagmentservice.sender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private String type;
    private DataPayload payload;

    public MessageModel(String type, String number, String text) {
        this.type = type;
        this.payload = new DataPayload(number, text);
    }
}
