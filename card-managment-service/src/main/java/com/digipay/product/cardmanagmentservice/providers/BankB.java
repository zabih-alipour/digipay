package com.digipay.product.cardmanagmentservice.providers;

public class BankB extends BankServiceProvider {
    public BankB() {
        super("https://second-payment-provider/cards/pay");
    }
}
