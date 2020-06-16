package com.bridgelabz.fundoonote.dto;

import com.bridgelabz.fundoonote.module.UserDetails;

public class ResponseDTO {

	public UserDTO userDTO;
	public String message;
	public int otp;
	public UserDetails userDetails;

	public ResponseDTO(String message, UserDetails userDetails) {
		this.message = message;
		this.userDetails = userDetails;
	}

	public ResponseDTO( String message,UserDTO userDTO) {
		this.userDTO = userDTO;
		this.message = message;
	}
	
	public ResponseDTO(String message, int otp) {
		this.message = message;
		this.otp = otp;
	}

	public ResponseDTO(String message) {
		this.message = message;
	}

}
