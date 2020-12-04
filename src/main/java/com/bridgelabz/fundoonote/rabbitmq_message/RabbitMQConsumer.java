package com.bridgelabz.fundoonote.rabbitmq_message;

import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class RabbitMQConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @RabbitListener(queues = "${rabbitMQ.Queue}")
    public void recievedMessage(Email email) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getEmailId());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getMessage());

        executorService.submit(()->{
            try {

                javaMailSender.send(mailMessage);

            } catch (Exception e) {
                throw new FundooUserException("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        });
    }
}
