package com.bridgelabz.fundoonote.module;

import javax.persistence.*;

import com.bridgelabz.fundoonote.util.JwtToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonote.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@ToString
@Setter
@Getter
@Component
public class UserDetails implements Serializable {

	public String id;
	@Id
	@Column(name = "user_Id")
	public String userId;
	public String firstName;
	public String lastName;
	@JsonIgnore
	public String service;
	public String email;
	@JsonIgnore
	public String password;
	@JsonIgnore
	public boolean isVerified = false;

	@Temporal(TemporalType.TIMESTAMP)
	public Date createdDate;

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(mappedBy = "userDetails", fetch = FetchType.LAZY,
	 cascade = CascadeType.ALL)
	private Set<NoteDetails> noteDetails;

	public UserDetails() {
	}

	public UserDetails(UserDTO fundooDto) {
		this.id = new JwtToken().generateToken(fundooDto.email);
		this.userId = Long.toString(UUID.randomUUID().getMostSignificantBits(), 36);
		this.createdDate = new Date();
		this.firstName = fundooDto.firstName;
		this.lastName = fundooDto.lastName;
		this.service = fundooDto.service;
		this.email = fundooDto.email;
		this.password = new BCryptPasswordEncoder().encode(fundooDto.password);
	}
}
