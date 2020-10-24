package com.digipay.product.notificationservice.provider;

import com.digipay.product.notificationservice.models.DataPayload;
import com.digipay.product.notificationservice.models.ProviderType;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

@Getter
public abstract class Provider {
    public final String url;
    private final ProviderType providerType;
    private RestTemplate restTemplate;

    public Provider(String url, ProviderType providerType, RestTemplate restTemplate) {
        this.url = url;
        this.providerType = providerType;
        this.restTemplate = restTemplate;
    }

    public void send(DataPayload dataPayload){
        if (!sendToUser(dataPayload)) {
            // TODO: 10/24/20 Put message in queue for retry
        }
    }

    protected abstract boolean sendToUser(DataPayload payload);

}
