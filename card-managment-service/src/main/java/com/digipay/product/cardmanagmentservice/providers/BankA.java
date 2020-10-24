package com.digipay.product.cardmanagmentservice.providers;

public class BankA extends BankServiceProvider{
    public BankA() {
        super("https://first-payment-provider/payments/transfer");
    }
}
