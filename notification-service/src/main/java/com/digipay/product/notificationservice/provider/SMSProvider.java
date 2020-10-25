package com.digipay.product.notificationservice.provider;

import com.digipay.product.notificationservice.models.DataPayload;
import com.digipay.product.notificationservice.models.ProviderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class SMSProvider extends Provider {
    public SMSProvider(@Value("${sms.provider}") String url, RestTemplate restTemplate) {
        super(url, ProviderType.SMS, restTemplate);
    }

    @Override
    protected boolean sendToUser(DataPayload payload) {
        Logger.getGlobal().info(
                "\n########### Message received: " +
                        "\n\t - Type:     SMS" +
                        "\n\t - Number:   " + payload.getTarget() +
                        "\n\t - Message:  " + payload.getMsg());
        // TODO: 10/24/20 Use restTemplate for sent message to provider
        return true;
    }
}
