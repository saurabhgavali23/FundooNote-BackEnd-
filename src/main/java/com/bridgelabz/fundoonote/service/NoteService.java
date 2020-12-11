package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtToken jwtToken;

    @Override
    public String saveNote(NoteDTO noteDTO, String userToken) {

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userTokens = Long.parseLong(userIdFromToken);

        Boolean validateToken = jwtToken.validateToken(userToken, userIdFromToken);

        if(!validateToken)
            throw new FundooNoteException("Token_Not_Valid", HttpStatus.BAD_REQUEST.value());

        UserDetails userDetails = userRepository.findById(userTokens)
                .orElseThrow(()-> new FundooNoteException("Invalid_user", HttpStatus.NOT_FOUND.value()));

        NoteDetails noteDetails = new NoteDetails(noteDTO);

        NoteDetails saveNoteDetails = noteRepository.save(noteDetails);

        userDetails.getNoteDetails().add(saveNoteDetails);

        UserDetails details = userRepository.save(userDetails);

        if(details == null){
            throw new FundooNoteException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value());
        }

        return "Note_Added_Successfully";
    }

    @Override
    public List getNoteList(String userToken) {

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userTokens = Long.parseLong(userIdFromToken);

        Boolean validateToken = jwtToken.validateToken(userToken, userIdFromToken);

        if(!validateToken)
            throw new FundooNoteException("Token_Not_Valid", HttpStatus.BAD_REQUEST.value());

        UserDetails userDetails = userRepository.findById(userTokens)
                .orElseThrow(()-> new FundooNoteException("Invalid_User", HttpStatus.BAD_REQUEST.value()));

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
    public List getPinNotes(String userToken) {

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List<NoteDetails> pinNoteList = noteRepository.getPinNoteList(userId);

        if(pinNoteList.isEmpty())
            throw new FundooNoteException("PinNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return pinNoteList;
    }

    @Override
    public List getArchiveNotes(String userToken) {

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List<NoteDetails> archiveNoteList = noteRepository.getArchiveNoteList(userId);

        if(archiveNoteList.isEmpty())
            throw new FundooNoteException("ArchiveNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return archiveNoteList;
    }

    @Override
    public List getTrashNotes(String userToken) {

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List<NoteDetails> trashNoteList = noteRepository.getTrashNoteList(userId);

        if(trashNoteList.isEmpty())
            throw new FundooNoteException("TrashNotes_Not_Found", HttpStatus.NOT_FOUND.value());

        return trashNoteList;
    }

    @Override
    public List getReminderNotes(String userToken) {

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

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
    public List searchNote(String userToken, String word) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        if(!jwtToken.validateToken(userToken, userDetails.id.toString()))
            throw new FundooUserException("Invalid_Token", HttpStatus.BAD_REQUEST.value());

        if(word == null)
            return new ArrayList();

        List<NoteDetails> noteDetailsList = noteRepository.searchNoteList(userDetails.id)
                .parallelStream()
                .filter(n -> n.title.contains(word.toLowerCase().replaceAll("\\s", "")) ||
                        n.description.contains(word.toLowerCase().replaceAll("\\s", "")))
                .collect(Collectors.toList());

        return noteDetailsList;
    }

    @Override
    public List getNotePages(String userToken, Integer pageNo, Integer pageSize) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        if(!jwtToken.validateToken(userToken, userDetails.id.toString()))
            throw new FundooUserException("Invalid_Token", HttpStatus.BAD_REQUEST.value());

        if(pageNo == null && pageSize == null)
            throw new FundooNoteException("Field_Must_Not_Be_Null", HttpStatus.BAD_REQUEST.value());

        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by("title").ascending());

        Page<NoteDetails> noteList = noteRepository.findAll(paging);

        if(noteList.hasContent())
            return noteList.getContent();

        return new ArrayList<NoteDetails>();
    }
}
