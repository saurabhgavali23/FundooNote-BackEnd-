package com.bridgelabz.fundoonote.module;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonote.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Setter
@Getter
@Component
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;

	public String firstName;
	public String lastName;
	public String gender;
	public String phoneNumber;
	public String email;
	public String password;
	public boolean isVerified = false;

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDetails() {
	}

	public UserDetails(UserDTO fundooDto) {

		this.firstName = fundooDto.firstName;
		this.lastName = fundooDto.lastName;
		this.gender = fundooDto.gender;
		this.phoneNumber = fundooDto.phoneNumber;
		this.email = fundooDto.email;
		this.password = new BCryptPasswordEncoder().encode(fundooDto.password);
	}
}
