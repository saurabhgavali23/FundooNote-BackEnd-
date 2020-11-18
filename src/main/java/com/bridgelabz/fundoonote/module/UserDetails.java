package com.bridgelabz.fundoonote.module;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonote.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@ToString
@Setter
@Getter
@Component
public class UserDetails implements Serializable {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Transient
	public String tokenId;

	public String firstName;
	public String lastName;
	@JsonIgnore
	public String service;
	public String email;
	@JsonIgnore
	public String password;
	@JsonIgnore
	public boolean isVerified = false;

	@JsonIgnore
	@Transient
	LocalDateTime now = LocalDateTime.now();

	@CreationTimestamp
	public LocalDateTime createdDate;

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<NoteDetails> noteDetails;

	public List<NoteDetails> getNoteDetails() {
		return noteDetails;
	}

	public UserDetails() {
	}

	public UserDetails(UserDTO fundooDto) {
		this.createdDate = now;
		this.firstName = fundooDto.firstName;
		this.lastName = fundooDto.lastName;
		this.service = fundooDto.service;
		this.email = fundooDto.email;
		this.password = new BCryptPasswordEncoder().encode(fundooDto.password);
	}
}
