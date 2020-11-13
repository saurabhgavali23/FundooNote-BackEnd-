package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.NoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteDetails, Integer> {

    @Query(value = "SELECT * FROM note_details u WHERE u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getNoteList(Long userId);
}
