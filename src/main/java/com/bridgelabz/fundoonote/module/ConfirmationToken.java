package com.bridgelabz.fundoonote.module;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long tokenId;

	@Column(name = "confirmation_token")
	public String confirmationToken;

	@Temporal(TemporalType.TIMESTAMP)
	public Date createdDate;
	
	@Column(name = "expiry_date")
	private Date expiryDate;

	@OneToOne(targetEntity = UserDetails.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	public UserDetails user;

	public ConfirmationToken() {
	}

	private void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	private void setExpiryDate(int minuts) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minuts);
		this.expiryDate = now.getTime();
	}
	
	public boolean isExpired() {
		return new Date().after(this.expiryDate);
	}

	public ConfirmationToken(UserDetails user) {
		this.user = user;
		this.createdDate = new Date();
		this.setExpiryDate(30);
		this.confirmationToken = UUID.randomUUID().toString();
	}
}
