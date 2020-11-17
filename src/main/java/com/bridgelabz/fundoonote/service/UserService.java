package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.exception.DataNotFoundException;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.ConfirmationTokenRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements IUserService {

	@Value("${localHostUrl}")
	private String url;

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
		String token = jwtToken.generateToken(userDetails.id.toString());

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(details.email);
		mailMessage.setSubject("Complete Registration");
		mailMessage.setText("To Confirm Your Account, please click here : "
				+url+"/fundoonote/confirm-account?token=" + token);
		emailSenderService.sendEmail(mailMessage);
		return "User Added Successfully";
	}

	@Override
	public void forgotPassword(UserDetails userDetails) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(userDetails.email);
		mailMessage.setSubject("Complete Password Reset");
		mailMessage.setText("To complete the password reset process, please click here: "
				+url+"/fundoonote/confirm-reset-password?token="+userDetails.id);
		emailSenderService.sendEmail(mailMessage);	
	}

	@Override
	public UserDetails loginUser(String email, String password) {

		UserDetails userDetails = userRepository.findByEmail(email)
				.orElseThrow(()-> new FundooNoteException("Invalid Email Id"));

		if (!encoder.matches(password, userDetails.password)) {
			throw new FundooNoteException("Invalid_Password");
		}

		UserDetails userEmailConfirmation = userRepository.findByEmailVerificatioin(email);
		if (userEmailConfirmation == null) {
			throw new FundooNoteException("Invalid_Account");
		}
		String userId = jwtToken.generateToken(userDetails.id.toString());
		userDetails.setTokenId(userId);
		return userDetails;
	}

	@Override
	public String verifyAccount(String userToken) {

		long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

		UserDetails token = userRepository.findById(userTokens)
				.orElseThrow(() -> new FundooNoteException("Invalid_link"));

		UserDetails user = userRepository.findByEmail(token.email)
				.orElseThrow(() -> new FundooNoteException("Invalid_Email"));

		if (!jwtToken.validateToken(userToken,token.id.toString()))
			throw new FundooNoteException("Link_Expired");

		user.setVerified(true);
		userRepository.save(user);
		return "User Verified";
	}

	@Override
	public void confirmPassword(String userToken) {

		long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

		UserDetails user = userRepository.findById(userTokens)
				.orElseThrow(() -> new FundooNoteException("Invalid_Email"));

		if(!jwtToken.validateToken(userToken,user.email)){
			throw new FundooNoteException("Invalid_link");
		}
		user.setVerified(true);
		userRepository.save(user);
	}

	@Override
	public void changePassword(UserDTO userDTO, String userToken) {

		long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

		UserDetails user = userRepository.findById(userTokens)
				.orElseThrow(() -> new FundooNoteException("Invalid_User"));

		if(!jwtToken.validateToken(userToken, user.email)){
			throw new FundooNoteException("Invalid_User");
		}

		String newPassword = encoder.encode(userDTO.password);
		user.setPassword(newPassword);
		userRepository.save(user);
	}

	@Override
	public List getAllUserRecords() {

		List<UserDetails> userDetailsList = userRepository.findAll();

		if(userDetailsList != null) {
			userDetailsList.stream()
					.forEach(item -> item.setTokenId(jwtToken.generateToken(item.id.toString())));
			return userDetailsList;
		}

		throw new DataNotFoundException("Users_Not_Found");
	}
}
