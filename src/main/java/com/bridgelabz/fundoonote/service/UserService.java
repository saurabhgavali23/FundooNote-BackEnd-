package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.util.JwtToken;
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

	@Autowired
	JwtToken jwtToken;

	@Override
	public String addUser(UserDTO userDTO) {
		
		UserDetails userDetails = new UserDetails(userDTO);
		UserDetails details = userRepository.save(userDetails);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(details.email);
		mailMessage.setSubject("Complete Registration");
		mailMessage.setText("To Confirm Your Account, please click here : "
				+ "http://localhost:8080/fundoonote/confirm-account?token=" + details.id);
		emailSenderService.sendEmail(mailMessage);
		return "User Added Successfully";
	}

	@Override
	public void forgotPassword(UserDetails userDetails) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(userDetails.email);
		mailMessage.setSubject("Complete Password Reset");
		mailMessage.setText("To complete the password reset process, please click here: "
				+"http://localhost:8080/fundoonote/confirm-reset-password?token="+userDetails.id);
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

	@Override
	public String verifyAccount(String userToken) {

		UserDetails token = userRepository.findById(userToken)
				.orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.LINK_IS_INVALID, "Invlid_link"));

		UserDetails user = userRepository.findByEmail(token.email)
				.orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL, "Invalid_Email"));

		if (!jwtToken.validateToken(userToken,token.email))
			throw new FundooNoteException(FundooNoteException.ExceptionType.LINK_IS_EXPIRED, "Link_Expired");

		user.setVerified(true);
		userRepository.save(user);
		return "User Verified";
	}

	@Override
	public void confirmPassword(String userToken) {
		UserDetails user = userRepository.findById(userToken)
				.orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL, "Invalid_Email"));

		if(!jwtToken.validateToken(userToken,user.email)){
			throw new FundooNoteException(FundooNoteException.ExceptionType.LINK_IS_INVALID, "Invlid_link");
		}
		user.setVerified(true);
		userRepository.save(user);
	}

	@Override
	public void changePassword(UserDTO userDTO, String userToken) {

		UserDetails user = userRepository.findById(userToken)
				.orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_USER, "Invalid_User"));

		if(!jwtToken.validateToken(userToken, user.email)){
			throw new FundooNoteException(FundooNoteException.ExceptionType.INVALID_USER, "Invlid_User");
		}

		String newPassword = encoder.encode(userDTO.password);
		user.setPassword(newPassword);
		userRepository.save(user);
	}

}
