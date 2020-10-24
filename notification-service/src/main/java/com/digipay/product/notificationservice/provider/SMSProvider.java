package com.digipay.product.notificationservice.provider;

import com.digipay.product.notificationservice.models.DataPayload;
import com.digipay.product.notificationservice.models.ProviderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SMSProvider extends Provider {
    public SMSProvider(@Value("${sms.provider}") String url, RestTemplate restTemplate) {
        super(url, ProviderType.SMS, restTemplate);
    }

    @Override
    protected boolean sendToUser(DataPayload payload) {
        // TODO: 10/24/20 Use restTemplate for sent message to provider
        return true;
    }
}
