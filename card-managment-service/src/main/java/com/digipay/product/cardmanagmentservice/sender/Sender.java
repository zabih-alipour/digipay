package com.digipay.product.cardmanagmentservice.sender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Value("${rabbitmq.topic.exchange-name}")
    private String topicExchangeName;

    private final RabbitTemplate rabbitTemplate;


    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void asSMS(String number, String text) {
        DataPayload payload = new DataPayload("sms", number, text);
        rabbitTemplate.convertAndSend(topicExchangeName, payload);
    }

    public void asFCM(String number, String text) {
        DataPayload payload = new DataPayload("fcm", number, text);
        rabbitTemplate.convertAndSend(topicExchangeName, payload);
    }

    @Getter
    @Setter
    private class DataPayload {
        private final String type;
        private final String target;
        private final String msg;

        public DataPayload(String type, String target, String msg) {
            this.type = type;
            this.target = target;
            this.msg = msg;
        }
    }
}
