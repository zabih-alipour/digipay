package com.digipay.product.notificationservice.util;

import com.digipay.product.notificationservice.models.MessageModel;
import com.digipay.product.notificationservice.models.ProviderType;
import com.digipay.product.notificationservice.patterns.ProviderPattern;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

@Component
@RabbitListener(queues = "${rabbitmq.queue-name}")
public class Receiver {
    private final ObjectMapper mapper;
    private final ProviderPattern providerPattern;

    private final CountDownLatch latch = new CountDownLatch(1);

    public Receiver(ObjectMapper mapper, ProviderPattern providerPattern) {
        this.mapper = mapper;
        this.providerPattern = providerPattern;
    }

    //    @RabbitHandler
    public void receiveMessage(byte[] message) throws JsonProcessingException {
        String msg = new String(message, StandardCharsets.UTF_8);
        MessageModel model = mapper.readValue(msg, MessageModel.class);
        providerPattern.getProvider(ProviderType.getInstance(model.getType()))
                .ifPresent(provider -> provider.send(model.getPayload()));

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
