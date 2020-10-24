package com.digipay.product.cardmanagmentservice.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String... params) {
        super("مقدار فیلد(های) ".concat(String.join(", ", params)).concat(" اشتباه است"));
    }
}
