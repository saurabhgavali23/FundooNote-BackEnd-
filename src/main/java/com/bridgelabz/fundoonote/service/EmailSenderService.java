package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.exception.FundooUserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.ConfirmationToken;
import com.bridgelabz.fundoonote.module.UserDetails;

import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender javaMailSender;

	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	public void sendEmail(UserDetails userDetail, ConfirmationToken token) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userDetail.email);
		mailMessage.setSubject("Complete Registration");
		mailMessage.setText("To Confirm Your Account, please click here : "
				+ "http://localhost:8080/fundoonote/confirm-account?token=" + token.confirmationToken);
		javaMailSender.send(mailMessage);
	}
	
	public <T> void sendEmail(SimpleMailMessage mailMessage) {
		
		executorService.submit(()->{
			try {
				
                javaMailSender.send(mailMessage);
                
            } catch (Exception e) {
                throw new FundooUserException("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
		});
	}
}
