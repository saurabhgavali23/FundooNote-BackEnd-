package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.CollaboratorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface CollaboratorRepository extends JpaRepository<CollaboratorDetails, Long> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE collaborator_details u SET u.email_id =?1, u.modified_date =?2 WHERE u.id =?3", nativeQuery = true)
    int updateCollaborator(String emailId, LocalDateTime modifiedDate, Long id);
}
