package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;

import java.util.List;

public interface INoteService {

    String saveNote(NoteDTO noteDTO, String userToken);

    List getNoteList(String userToken);

    String updatePin(NoteDTO noteDTO);

    String updateArchived(NoteDTO noteDTO);

    String updateTrash(NoteDTO noteDTO);

    String updateColor(NoteDTO noteDTO);

    List getPinNotes(String userToken);

    List getArchiveNotes(String userToken);

    List getTrashNotes(String userToken);

    String updateTitleAndDescription(NoteDTO noteDTO);

    String deleteNotePermanently(NoteDTO noteDTO);

    String updateReminder(NoteDTO noteDTO);

    List getReminderNotes(String userToken);
}
