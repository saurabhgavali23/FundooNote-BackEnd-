package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.CollaboratorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<CollaboratorDetails, Long> {


}
