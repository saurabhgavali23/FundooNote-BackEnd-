package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.Email;
import com.bridgelabz.fundoonote.rabbitmq_message.MessageProducer;
import com.bridgelabz.fundoonote.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

	@Value("${localHostUrl}")
	private String url;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	JwtToken jwtToken;

	@Autowired
	MessageProducer messageProducer;

	@Override
	public String addUser(UserDTO userDTO) {

		Optional<UserDetails> byEmailId = userRepository.findByEmail(userDTO.email);

		if (byEmailId.isPresent()) {
			throw new FundooUserException("User_Already_Registered", HttpStatus.CONFLICT.value());
		}
		
		UserDetails userDetails = new UserDetails(userDTO);
		UserDetails details = userRepository.save(userDetails);
		String token = jwtToken.generateToken(userDetails.id.toString());

		String path = "To Confirm Your Account, please click here : "
				+url+"/fundoonote/confirm-account?token=" + token;

		messageProducer.sendMessage(new Email(details.email, "Complete Registration", path));

		return "User Added Successfully";
	}

	@Override
	public String forgotPassword(String email) {

		UserDetails userDetails = userRepository.findByEmail(email)
				.orElseThrow(() -> new FundooUserException("Invalid Email Id", HttpStatus.BAD_REQUEST.value()));
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(userDetails.email);
		mailMessage.setSubject("Complete Password Reset");
		mailMessage.setText("To complete the password reset process, please click here: "
				+url+"/fundoonote/confirm-reset-password?token="+userDetails.id);
		emailSenderService.sendEmail(mailMessage);

		return "Password Reset Link Sent to Email";
	}

	@Override
	public UserDetails loginUser(String email, String password) {

		UserDetails userDetails = userRepository.findByEmail(email)
				.orElseThrow(()-> new FundooUserException("Invalid Email Id", HttpStatus.BAD_REQUEST.value()));

		if (!encoder.matches(password, userDetails.password)) {
			throw new FundooUserException("Invalid_Password", HttpStatus.BAD_REQUEST.value());
		}

		UserDetails userEmailConfirmation = userRepository.findByEmailVerificatioin(email);
		if (userEmailConfirmation == null) {
			throw new FundooUserException("Invalid_Account", HttpStatus.BAD_REQUEST.value());
		}
		String userId = jwtToken.generateToken(userDetails.id.toString());
		userDetails.setTokenId(userId);
		return userDetails;
	}

	@Override
	public String verifyAccount(String userToken) {

		long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

		UserDetails token = userRepository.findById(userTokens)
				.orElseThrow(() -> new FundooUserException("Invalid_link", HttpStatus.BAD_REQUEST.value()));

		UserDetails user = userRepository.findByEmail(token.email)
				.orElseThrow(() -> new FundooUserException("Invalid_Email", HttpStatus.BAD_REQUEST.value()));

		if (!jwtToken.validateToken(userToken,token.id.toString()))
			throw new FundooUserException("Link_Expired", HttpStatus.BAD_REQUEST.value());

		user.setVerified(true);
		userRepository.save(user);
		return "User Verified";
	}

	@Override
	public Boolean confirmPassword(String userToken) {

		long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

		UserDetails user = userRepository.findById(userTokens)
				.orElseThrow(() -> new FundooUserException("Invalid_Email", HttpStatus.BAD_REQUEST.value()));

		if(!jwtToken.validateToken(userToken,user.email)){
			throw new FundooUserException("Invalid_link", HttpStatus.BAD_REQUEST.value());
		}
		user.setVerified(true);
		UserDetails userDetails = userRepository.save(user);

		if(userDetails != null)
			return true;

		return false;
	}

	@Override
	public Boolean changePassword(UserDTO userDTO, String userToken) {

		long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

		UserDetails user = userRepository.findById(userTokens)
				.orElseThrow(() -> new FundooUserException("Invalid_User", HttpStatus.NOT_FOUND.value()));

		if(!jwtToken.validateToken(userToken, user.email)){
			throw new FundooUserException("Invalid_User", HttpStatus.NOT_FOUND.value());
		}

		String newPassword = encoder.encode(userDTO.password);
		user.setPassword(newPassword);
		UserDetails userDetails = userRepository.save(user);

		if(userDetails != null)
			return true;

		return false;
	}

	@Override
	public List getAllUserRecords() {

		List<UserDetails> userDetailsList = userRepository.findAll();

		if(userDetailsList != null) {
			userDetailsList.stream()
					.forEach(item -> item.setTokenId(jwtToken.generateToken(item.id.toString())));
			return userDetailsList;
		}

		throw new FundooUserException("Users_Not_Found", HttpStatus.NOT_FOUND.value());
	}
}
