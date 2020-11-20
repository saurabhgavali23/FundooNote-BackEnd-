package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.LabelRepository;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtToken jwtToken;

    @Autowired
    LabelRepository labelRepository;

    @Override
    public void SaveNote(NoteDTO noteDTO, UserDetails userDetails) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        NoteDetails saveNoteDetails = noteRepository.save(noteDetails);

        userDetails.getNoteDetails().add(saveNoteDetails);

        UserDetails details = userRepository.save(userDetails);

        if(details == null){
            throw new FundooNoteException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value());
        }
    }

    @Override
    public List getNoteList(UserDetails userDetails) {

        List<NoteDetails> noteList = noteRepository.getNoteList(userDetails.id);

        if(noteList == null)
            throw new FundooNoteException("Notes_Not_Found", HttpStatus.NOT_FOUND.value());

        return noteList;
    }

    @Override
    public String updatePin(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.isPined == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        int details = noteRepository.updatePin(noteDetails.isPined, noteDetails.modifiedDate, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("PinNote_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "PinNote Updated";
    }

    @Override
    public String updateArchived(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.isArchived == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        int details = noteRepository.updateArchive(noteDetails.isArchived, noteDetails.modifiedDate, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("ArchiveNote_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "ArchiveNote Updated";
    }

    @Override
    public String updateTrash(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.isDeleted == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        int details = noteRepository.updateTrash(noteDetails.isDeleted, noteDetails.modifiedDate, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("TrashNote_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "TrashNote Updated";
    }

    @Override
    public String updateColor(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.color == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        int details = noteRepository.updateColor(noteDetails.color, noteDetails.modifiedDate, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("ColorNote_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "ColorNote Updated";
    }

    @Override
    public String updateTitleAndDescription(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.title == null && noteDTO.description == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        int details = noteRepository.updateTitleAndDescription(noteDetails.title, noteDetails.description, noteDetails.modifiedDate, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("TitleAndDescriptionNote_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "TitleDescriptionNote Updated";
    }

    @Override
    public String updateReminder(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.reminder == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        int details = noteRepository.updateReminder(noteDetails.reminder, noteDetails.modifiedDate, noteDetails.noteId);

        if(details == 0){
            throw new FundooNoteException("Reminder_Note_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "Reminder Note Updated";
    }

    @Override
    public List getPinNotes(Long userToken) {

        List<NoteDetails> pinNoteList = noteRepository.getPinNoteList(userToken);

        if(pinNoteList.isEmpty())
            throw new FundooNoteException("PinNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return pinNoteList;
    }

    @Override
    public List getArchiveNotes(Long userId) {

        List<NoteDetails> archiveNoteList = noteRepository.getArchiveNoteList(userId);

        if(archiveNoteList.isEmpty())
            throw new FundooNoteException("ArchiveNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return archiveNoteList;
    }

    @Override
    public List getTrashNotes(Long userId) {

        List<NoteDetails> trashNoteList = noteRepository.getTrashNoteList(userId);

        if(trashNoteList.isEmpty())
            throw new FundooNoteException("TrashNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return trashNoteList;
    }

    @Override
    public List getReminderNotes(Long userId) {

        List<NoteDetails> reminderNoteList = noteRepository.getReminderNoteList(userId);

        if(reminderNoteList.isEmpty())
            throw new FundooNoteException("ReminderNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return reminderNoteList;
    }

    @Override
    public String deleteNotePermanently(NoteDTO noteDTO) {

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        Optional<NoteDetails> noteId = noteRepository.findById(noteDetails.noteId);

        if(!noteId.isPresent())
            throw new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value());

        if(noteDTO.isDeleted == null)
            throw new FundooNoteException("Field_Must_Not_be_Null", HttpStatus.BAD_REQUEST.value());

        if(noteDetails.isDeleted == true)
            noteRepository.deleteById(noteDetails.noteId);
        else
            return "Note_Updated";

        return "Note_Deleted_Successfully";
    }

    @Override
    public LabelDetails addLabel(String userToken, LabelDTO labelDTO) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        LabelDetails labelDetails = new LabelDetails(labelDTO);

        LabelDetails saveLabelDetails = labelRepository.save(labelDetails);

        userDetails.getLabelDetails().add(saveLabelDetails);
        userRepository.save(userDetails);

        if(saveLabelDetails == null){
            throw new FundooNoteException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value());
        }

        return saveLabelDetails;
    }

    @Override
    public String addLabelInNote(String userToken, Long labelId, Long noteId) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        NoteDetails noteDetails = noteRepository.findById(noteId)
                .orElseThrow(() -> new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value()));

        LabelDetails labelDetails = labelRepository.findById(labelId)
                .orElseThrow(() -> new FundooNoteException("Label_Not_Found", HttpStatus.NOT_FOUND.value()));

        noteDetails.getLabelDetails().add(labelDetails);

        noteRepository.save(noteDetails);

        return "Note_Label_Saved";
    }

    @Override
    public String updateLabel(String userToken, LabelDTO labelDTO) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        LabelDetails labelDetails = new LabelDetails(labelDTO);

        labelRepository.findById(labelDTO.labelId)
                .orElseThrow(() -> new FundooNoteException("Label_Not_Found", HttpStatus.NOT_FOUND.value()));

        int i = labelRepository.updateLabel(labelDetails.labelName, labelDetails.modifiedDate, labelDTO.labelId, userId);

        if(i == 0){
            throw new FundooNoteException("Label_Not_Update", HttpStatus.NOT_IMPLEMENTED.value());
        }
        return "Label_Updated_Successfully";
    }

    @Override
    public List getLabelDetails(String userToken) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        List<LabelDetails> labelList = labelRepository.getLabelList(userId);

        if(labelList.isEmpty())
            throw new FundooNoteException("Label_Not_Found", HttpStatus.NOT_FOUND.value());

        return labelList;
    }
}
