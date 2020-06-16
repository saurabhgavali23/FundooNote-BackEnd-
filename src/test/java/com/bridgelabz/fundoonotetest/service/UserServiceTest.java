package com.bridgelabz.fundoonotetest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;

	UserDTO userDTO;
	UserDetails userDetails;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void givenUserDetails_WhenDetailSaved_ThenReturnStatusOK() {
		userDTO = new UserDTO("Saurabh","Gavali","male","9665234018","gavalisaurabh02@gmail.com","Saurabh@02");
		UserDetails userDetails = new UserDetails(userDTO);
		when(userRepository.save(any())).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(userDetails);
		UserDetails userDetails1 = userService.addUser(userDTO);
		Assert.assertEquals(userDetails,userDetails1);
	}
}
