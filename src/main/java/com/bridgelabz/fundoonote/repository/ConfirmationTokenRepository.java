package com.bridgelabz.fundoonote.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoonote.module.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

	Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

	@Transactional
	@Modifying
	@Query(value = "UPDATE confirmation_token t set confirmation_token=?1, created_date=?2 where t.user_id=?3", nativeQuery = true)
	void saveNewTokenAndTime(String Token,Date date, String id);
}
