package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.NoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteDetails, String> {
}
