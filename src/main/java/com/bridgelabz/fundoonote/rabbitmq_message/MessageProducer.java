package com.bridgelabz.fundoonote.rabbitmq_message;

import com.bridgelabz.fundoonote.configuration.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {

        System.out.println(new Date());

        rabbitTemplate.convertAndSend(RabbitMqConfig.ROUTING_KEY, message);

        System.out.println("Is listener returned ::: " + rabbitTemplate.isReturnListener());
        System.out.println(new Date());
    }
}
