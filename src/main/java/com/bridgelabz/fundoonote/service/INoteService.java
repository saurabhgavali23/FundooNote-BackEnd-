package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.module.UserDetails;

import java.util.List;

public interface INoteService {

    public String saveNote(NoteDTO noteDTO, String userToken);

    public List getNoteList(String userToken);

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

    LabelDetails addLabel(String userToken, LabelDTO labelName);

    String addLabelInNote(String userToken, Long labelId, Long noteId);

    String updateLabel(String userToken, LabelDTO labelDTO);

    List getLabelDetails(String userToken);
}
