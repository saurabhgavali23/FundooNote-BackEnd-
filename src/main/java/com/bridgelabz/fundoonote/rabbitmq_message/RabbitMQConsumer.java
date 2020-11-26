package com.bridgelabz.fundoonote.rabbitmq_message;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @Autowired
    Queue queue;

    @RabbitListener(queues = "#{queue.getName()}")
    public void recievedMessage(final String message) {

        System.out.println("Recieved Message: " + message);
    }
}
