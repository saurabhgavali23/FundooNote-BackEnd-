package com.bridgelabz.fundoonote.repository;

import com.bridgelabz.fundoonote.module.NoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteDetails, Long> {

    @Query(value = "SELECT * FROM note_details u WHERE u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getNoteList(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.is_pined =?1, u.modified_date =?2 WHERE u.note_id =?3", nativeQuery = true)
    int updatePin(Boolean isPined, LocalDateTime modifiedDate, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.is_archived =?1, u.modified_date =?2 WHERE u.note_id =?3", nativeQuery = true)
    int updateArchive(Boolean isArchived, LocalDateTime modifiedDate, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.is_deleted =?1, u.modified_date =?2 WHERE u.note_id =?3", nativeQuery = true)
    int updateTrash(Boolean isDeleted, LocalDateTime modifiedDate, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.color =?1, u.modified_date =?2 WHERE u.note_id =?3", nativeQuery = true)
    int updateColor(String color, LocalDateTime modifiedDate, Long noteId);

    @Query(value = "SELECT * FROM note_details u WHERE u.is_pined = 1 AND u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getPinNoteList(Long userToken);

    @Query(value = "SELECT * FROM note_details u WHERE u.is_archived = 1 AND u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getArchiveNoteList(Long userId);

    @Query(value = "SELECT * FROM note_details u WHERE u.is_deleted = 1 AND u.user_id =?1", nativeQuery = true)
    List<NoteDetails> getTrashNoteList(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.title =?1, u.description =?2, u.modified_date =?3 WHERE u.note_id =?4", nativeQuery = true)
    int updateTitleAndDescription(String title, String description, LocalDateTime modifiedDate, Long noteId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE note_details u SET u.reminder =?1, u.modified_date =?2 WHERE u.note_id =?3", nativeQuery = true)
    int updateReminder(String reminder, LocalDateTime modifiedDate, Long noteId);

    @Query(value = "SELECT * FROM note_details u WHERE u.user_id =?1 AND u.reminder IS NOT NULL", nativeQuery = true)
    List<NoteDetails> getReminderNoteList(Long userId);

    @Query(value = "SELECT * FROM note_details u WHERE u.user_id =?1", nativeQuery = true)
    List<NoteDetails> searchNoteList(Long id);
}
