package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.exception.FundooNoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.ConfirmationToken;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.ConfirmationTokenRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public String addUser(UserDTO userDTO) {
		
		UserDetails userDetails = new UserDetails(userDTO);
		UserDetails details = userRepository.save(userDetails);

		ConfirmationToken confirmationToken = new ConfirmationToken(userDetails);
		confirmationTokenRepository.save(confirmationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(details.email);
		mailMessage.setSubject("Complete Registration");
		mailMessage.setText("To Confirm Your Account, please click here : "
				+ "http://localhost:8080/fundoonote/confirm-account?token=" + confirmationToken.confirmationToken);
		emailSenderService.sendEmail(mailMessage);
		return "User Added Successfully";
	}

	@Override
	public void forgotPassword(UserDetails findByEmail) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		ConfirmationToken token = new ConfirmationToken(findByEmail);
		confirmationTokenRepository.saveNewTokenAndTime(token.confirmationToken, token.createdDate, token.user.id);
		
		mailMessage.setTo(findByEmail.email);
		mailMessage.setSubject("Complete Password Reset");
		mailMessage.setText("To complete the password reset process, please click here: "
				+"http://localhost:8080/fundoonote/confirm-reset-password?token="+token.confirmationToken);
		emailSenderService.sendEmail(mailMessage);	
	}

	@Override
	public UserDetails loginUser(String email, String password) {

		UserDetails userDetails = userRepository.findByEmail(email)
				.orElseThrow(()-> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL,"Invalid Email Id"));

		if (!encoder.matches(password, userDetails.password)) {
			throw new FundooNoteException(FundooNoteException.ExceptionType.INVALID_PASSWORD, "Invalid_Password");
		}

		UserDetails userEmailConfirmation = userRepository.findByEmailVerificatioin(email);
		if (userEmailConfirmation == null) {
			throw new FundooNoteException(FundooNoteException.ExceptionType.ACCOUNT_NOT_VALID, "Invalid_Account");
		}
		return userDetails;
	}
}
