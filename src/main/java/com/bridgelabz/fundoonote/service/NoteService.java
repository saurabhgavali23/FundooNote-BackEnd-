package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.ReminderDetails;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ReminderRepository reminderRepository;

    @Override
    public void SaveNote(NoteDTO noteDTO, UserDetails userDetails) {

        NoteDetails noteDetails = new NoteDetails(noteDTO, userDetails);

        NoteDetails details = noteRepository.save(noteDetails);

        ReminderDetails reminderDetails = new ReminderDetails(noteDetails, noteDTO);
        reminderRepository.save(reminderDetails);

        if(details == null){
            throw new FundooNoteException("Note_Not_Save");
        }
    }

    @Override
    public List getNoteList(UserDetails userDetails) {

        List<NoteDetails> noteList = noteRepository.getNoteList(userDetails.id);

        if(noteList == null)
            throw new FundooNoteException("Notes_Not_Found");

        return noteList;
    }

    @Override
    public String updatePin(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found");

        if(noteDTO.isPined == null)
            throw new FundooNoteException("Field_Must_Not_be_Null");

        int details = noteRepository.updatePin(noteDetails.isPined, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("PinNote_Not_Update");
        }
        return "PinNote Updated";
    }

    @Override
    public String updateArchived(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found");

        if(noteDTO.isArchived == null)
            throw new FundooNoteException("Field_Must_Not_be_Null");

        int details = noteRepository.updateArchive(noteDetails.isArchived, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("ArchiveNote_Not_Update");
        }
        return "ArchiveNote Updated";
    }

    @Override
    public String updateTrash(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found");

        if(noteDTO.isDeleted == null)
            throw new FundooNoteException("Field_Must_Not_be_Null");

        int details = noteRepository.updateTrash(noteDetails.isDeleted, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("TrashNote_Not_Update");
        }
        return "TrashNote Updated";
    }

    @Override
    public String updateColor(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found");

        if(noteDTO.color == null)
            throw new FundooNoteException("Field_Must_Not_be_Null");

        int details = noteRepository.updateColor(noteDetails.color, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("ColorNote_Not_Update");
        }
        return "ColorNote Updated";
    }

    @Override
    public String updateTitleAndDescription(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found");

        if(noteDTO.title == null && noteDTO.description == null)
            throw new FundooNoteException("Field_Must_Not_be_Null");

        int details = noteRepository.updateTitleAndDescription(noteDetails.title,noteDetails.description, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("TitleAndDescriptionNote_Not_Update");
        }
        return "TitleDescriptionNote Updated";
    }

    @Override
    public List getPinNotes(Long userToken) {

        List<NoteDetails> pinNoteList = noteRepository.getPinNoteList(userToken);

        if(pinNoteList.isEmpty())
            throw new FundooNoteException("PinNotes_Not_Found");

        return pinNoteList;
    }

    @Override
    public List getArchiveNotes(Long userId) {

        List<NoteDetails> archiveNoteList = noteRepository.getArchiveNoteList(userId);

        if(archiveNoteList.isEmpty())
            throw new FundooNoteException("PinNotes_Not_Found");

        return archiveNoteList;
    }

    @Override
    public List getTrashNotes(Long userId) {

        List<NoteDetails> trashNoteList = noteRepository.getTrashNoteList(userId);

        if(trashNoteList.isEmpty())
            throw new FundooNoteException("PinNotes_Not_Found");

        return trashNoteList;
    }

    @Override
    public String deleteNotePermanently(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found");

        if(noteDTO.isDeleted == null)
            throw new FundooNoteException("Field_Must_Not_be_Null");

        if(noteDetails.isDeleted == true)
            noteRepository.deleteById(noteDetails.noteId);
        else
            return "Note_Updated";

        return "Note_Deleted_Successfully";
    }
}
