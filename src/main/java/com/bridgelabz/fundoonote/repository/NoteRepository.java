package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.NoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface NoteRepository extends JpaRepository<NoteDetails, Long> {

    @Query(value = "SELECT * FROM note_details u WHERE u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getNoteList(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.is_pined =?1 WHERE u.note_id =?2", nativeQuery = true)
    int updatePin(Boolean isPined, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.is_archived =?1 WHERE u.note_id =?2", nativeQuery = true)
    int updateArchive(Boolean isArchived, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.is_deleted =?1 WHERE u.note_id =?2", nativeQuery = true)
    int updateTrash(Boolean isDeleted, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.color =?1 WHERE u.note_id =?2", nativeQuery = true)
    int updateColor(String color, Long noteId);

    @Query(value = "SELECT * FROM note_details u WHERE u.is_pined = 1 AND u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getPinNoteList(Long userToken);

    @Query(value = "SELECT * FROM note_details u WHERE u.is_archived = 1 AND u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getArchiveNoteList(Long userId);
}
