package com.bridgelabz.fundoonote.rabbitmq_message;

import com.bridgelabz.fundoonote.module.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmqExchange}")
    private String exchange;

    @Value("${rabbitmqRoutingKey}")
    private String routingKey;

    public void sendMessage(Email email) {

        rabbitTemplate.convertAndSend(exchange, routingKey, email);
    }
}
