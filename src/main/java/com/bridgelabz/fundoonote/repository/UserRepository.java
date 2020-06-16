package com.bridgelabz.fundoonote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.module.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {

	Optional<UserDetails> findByEmail(String email);

	@Query(value = "SELECT * FROM user_details u WHERE u.email=?1 and u.password=?2", nativeQuery=true)
	UserDetails findByEmailAndPass(String email, String password);

	@Query(value = "SELECT * FROM user_details u WHERE u.email=?1 and u.is_verified = 1", nativeQuery=true)
	UserDetails findByEmailVerificatioin(String email);
}
