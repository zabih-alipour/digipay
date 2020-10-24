package com.digipay.product.cardmanagmentservice.exceptions;

public class CardServiceException extends RuntimeException {

    public CardServiceException(String message, String... params) {
        super(params == null ? message : message.concat(" ").concat(String.join(", ", params)));
    }
}
