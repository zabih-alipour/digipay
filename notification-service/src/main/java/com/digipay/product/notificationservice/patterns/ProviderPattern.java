package com.digipay.product.notificationservice.patterns;

import com.digipay.product.notificationservice.provider.FCMProvider;
import com.digipay.product.notificationservice.provider.Provider;
import com.digipay.product.notificationservice.models.ProviderType;
import com.digipay.product.notificationservice.provider.SMSProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProviderPattern {
    private List<Provider> providers;

    public ProviderPattern(List<Provider> providers) {
        this.providers = providers;
    }

    public Optional<Provider> getProvider(ProviderType providerType) {
        return providers.stream()
                .filter(p-> p.getProviderType().equals(providerType))
                .findFirst();

    }
}
