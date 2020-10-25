package com.digipay.product.cardmanagmentservice.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Value("${rabbitmq.topic.exchange-name}")
    private String topicExchangeName;
    private final AmqpTemplate rabbitTemplate;

    public Sender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void asSMS(String number, String text) {
        MessageModel payload = new MessageModel("sms", number, text);
        rabbitTemplate.convertAndSend(topicExchangeName, "notification", payload);
    }

    public void asFCM(String number, String text) {
        MessageModel payload = new MessageModel("fcm", number, text);
        rabbitTemplate.convertAndSend(topicExchangeName, payload);
    }
}
