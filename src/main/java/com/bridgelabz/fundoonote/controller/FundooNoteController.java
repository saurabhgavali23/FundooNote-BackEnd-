package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.NoteService;
import com.bridgelabz.fundoonote.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fundoonote/note")
@CrossOrigin("*")
public class FundooNoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtToken jwtToken;

    @PostMapping("/save_note")
    public ResponseEntity<ResponseDTO> saveNote(@Valid @RequestHeader("token") String userToken, @RequestBody NoteDTO noteDTO, BindingResult result) {

        if (result.hasErrors()) {
            throw new FundooNoteException("Invalid_Data", HttpStatus.BAD_REQUEST.value());
        }

        String responseMessage = noteService.SaveNote(noteDTO, userToken);

        ResponseDTO responseDTO = new ResponseDTO(responseMessage);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/noteList")
    public ResponseEntity<ResponseDTO> noteList(@RequestHeader("token") String userToken){

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userTokens = Long.parseLong(userIdFromToken);

        Boolean validateToken = jwtToken.validateToken(userToken, userIdFromToken);

        if(!validateToken)
            throw new FundooNoteException("Token_Not_Valid", HttpStatus.BAD_REQUEST.value());

        UserDetails userDetails = userRepository.findById(userTokens)
                .orElseThrow(()-> new FundooNoteException("Invalid_User", HttpStatus.BAD_REQUEST.value()));

        List noteList = noteService.getNoteList(userDetails);

        ResponseDTO responseDTO = new ResponseDTO(noteList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/pinUnpinNote")
    public ResponseEntity<ResponseDTO> updatePin(@RequestBody NoteDTO noteDTO){

        String message = noteService.updatePin(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/archiveNote")
    public ResponseEntity<ResponseDTO> updateArchive(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateArchived(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/trashNote")
    public ResponseEntity<ResponseDTO> updateTrash(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateTrash(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/colorNote")
    public ResponseEntity<ResponseDTO> updateColor(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateColor(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/titleAndDescriptionNote")
    public ResponseEntity<ResponseDTO> updateTitleAndDescription(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateTitleAndDescription(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/reminderNote")
    public ResponseEntity<ResponseDTO> updateReminder(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateReminder(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/pinNoteList")
    public ResponseEntity<ResponseDTO> getPinNote(@RequestHeader("token") String userToken){

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List pinNote = noteService.getPinNotes(userId);

        ResponseDTO responseDTO = new ResponseDTO(pinNote);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/archiveNoteList")
    public ResponseEntity<ResponseDTO> getArchiveNotes(@RequestHeader("token") String userToken){

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List archiveNotes = noteService.getArchiveNotes(userId);

        ResponseDTO responseDTO = new ResponseDTO(archiveNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/trashNoteList")
    public ResponseEntity<ResponseDTO> getTrashNotes(@RequestHeader("token") String userToken){

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List trashNotes = noteService.getTrashNotes(userId);

        ResponseDTO responseDTO = new ResponseDTO(trashNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/reminderNoteList")
    public ResponseEntity<ResponseDTO> getReminderNotes(@RequestHeader("token") String userToken){

        String userIdFromToken = jwtToken.getUserIdFromToken(userToken);
        long userId = Long.parseLong(userIdFromToken);

        Optional<UserDetails> byId = userRepository.findById(userId);

        if(!byId.isPresent())
            throw new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value());

        List reminderNotes = noteService.getReminderNotes(userId);

        ResponseDTO responseDTO = new ResponseDTO(reminderNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/note")
    public ResponseEntity<ResponseDTO> deleteNotePermanently(@RequestBody NoteDTO noteDTO){

        String message = noteService.deleteNotePermanently(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/label")
    public ResponseEntity<ResponseDTO> addLabels(@RequestHeader("token") String userToken, @RequestBody LabelDTO labelDTO){

        LabelDetails responseLabelDetails = noteService.addLabel(userToken, labelDTO);

        ResponseDTO responseDTO = new ResponseDTO(responseLabelDetails);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/labelNote")
    public ResponseEntity<ResponseDTO> addLabelInNote(@RequestHeader("token") String userToken, @RequestHeader("labelId") Long labelId,
                                                      @RequestHeader("noteId") Long noteId){

        String message = noteService.addLabelInNote(userToken, labelId, noteId);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/updateLabel")
    public ResponseEntity<ResponseDTO> updateLabel(@RequestHeader("token") String userToken,
                                                    @RequestBody LabelDTO labelDTO){

        String message = noteService.updateLabel(userToken, labelDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/labelList")
    public ResponseEntity<ResponseDTO> getLabelDetails(@RequestHeader("token") String userToken){

        List labelDetails = noteService.getLabelDetails(userToken);

        ResponseDTO responseDTO = new ResponseDTO(labelDetails);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
