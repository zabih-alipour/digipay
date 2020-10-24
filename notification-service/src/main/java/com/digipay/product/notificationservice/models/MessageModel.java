package com.digipay.product.notificationservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel {
    private String type;
    private DataPayload payload;
}
