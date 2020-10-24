package com.digipay.product.notificationservice.models;

import java.util.Arrays;

public enum ProviderType {
    SMS, FCM;

    public static ProviderType getInstance(String code) {
        return Arrays.stream(values()).filter(p -> p.name().equalsIgnoreCase(code))
                .findFirst()
                .orElse(SMS);
    }
}
