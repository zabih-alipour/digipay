package com.digipay.product.cardmanagmentservice.patterns;

import com.digipay.product.cardmanagmentservice.providers.BankA;
import com.digipay.product.cardmanagmentservice.providers.BankB;
import com.digipay.product.cardmanagmentservice.providers.BankServiceProvider;
import org.springframework.stereotype.Component;

@Component
public class BankStrategyPattern {

    public BankServiceProvider getProvider(String cardNumber) {
        if (cardNumber.startsWith("6037"))
            return new BankA();
        else return new BankB();
    }
}
