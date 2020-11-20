package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.UserDetails;

import java.util.List;

public interface INoteService {

    public void SaveNote(NoteDTO noteDTO, UserDetails userDetails);

    public List getNoteList(UserDetails userDetails);

    String updatePin(NoteDTO noteDTO);

    String updateArchived(NoteDTO noteDTO);

    String updateTrash(NoteDTO noteDTO);

    String updateColor(NoteDTO noteDTO);

    List getPinNotes(Long userToken);

    List getArchiveNotes(Long userId);

    List getTrashNotes(Long userId);

    String updateTitleAndDescription(NoteDTO noteDTO);

    String deleteNotePermanently(NoteDTO noteDTO);

    String updateReminder(NoteDTO noteDTO);

    List getReminderNotes(Long userId);

    LabelDetails addLabel(String userToken, LabelDTO labelName);

    String addLabelInNote(String userToken, Long labelId, Long noteId);
}
