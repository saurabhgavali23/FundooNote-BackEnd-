package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.Email;
import com.bridgelabz.fundoonote.rabbitmq_message.RabbitMQProducer;
import com.bridgelabz.fundoonote.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

	@Value("${localHostUrl}")
	private String url;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	JwtToken jwtToken;

	@Autowired
	RabbitMQProducer rabbitMQProducer;

	@Override
	public String addUser(UserDTO userDTO) {

		log.debug("inside add user service method");

		Optional<UserDetails> byEmailId = userRepository.findByEmail(userDTO.email);

		if (byEmailId.isPresent()) {
			log.error("user already exist");
			throw new FundooUserException("User_Already_Registered", HttpStatus.CONFLICT.value());
		}
		
		UserDetails userDetails = new UserDetails(userDTO);
		UserDetails details = userRepository.save(userDetails);
		String token = jwtToken.generateToken(userDetails.id.toString());

		log.info("user crated with this id "+ details.id);

		String path = "To Confirm Your Account, please click here : "
				+url+"/user/confirm-account?token=" + token;

		rabbitMQProducer.sendMessage(new Email(details.email, "Complete Registration", path));
		log.debug("verification mail sent");

		return "User Added Successfully";
	}

	@Override
	public String forgotPassword(String email) {

		UserDetails userDetails = userRepository.findByEmail(email)
				.orElseThrow(() -> new FundooUserException("Invalid Email Id", HttpStatus.BAD_REQUEST.value()));

		String userIdFromToken = jwtToken.generateToken(userDetails.id.toString());

		String path = "To complete the password reset process, please click here: "
				+url+"/user/confirm-reset-password?token="+userIdFromToken;

		rabbitMQProducer.sendMessage(new Email(userDetails.email, "Complete Password Reset", path));

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

		if(!jwtToken.validateToken(userToken,user.id.toString())){
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

		if(!jwtToken.validateToken(userToken, user.id.toString())){
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
