package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.LabelDetails;
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

@RestController
@RequestMapping("/note")
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

    @PostMapping("/saveNote")
    public ResponseEntity<ResponseDTO> saveNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader("token") String userToken, BindingResult result) {

        if (result.hasErrors()) {
            throw new FundooNoteException("Invalid_Data", HttpStatus.BAD_REQUEST.value());
        }

        String responseMessage = noteService.saveNote(noteDTO, userToken);

        ResponseDTO responseDTO = new ResponseDTO(responseMessage);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/noteList")
    public ResponseEntity<ResponseDTO> noteList(@RequestHeader("token") String userToken){

        List noteList = noteService.getNoteList(userToken);

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

        List pinNote = noteService.getPinNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(pinNote);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/archiveNoteList")
    public ResponseEntity<ResponseDTO> getArchiveNotes(@RequestHeader("token") String userToken){

        List archiveNotes = noteService.getArchiveNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(archiveNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/trashNoteList")
    public ResponseEntity<ResponseDTO> getTrashNotes(@RequestHeader("token") String userToken){

        List trashNotes = noteService.getTrashNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(trashNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/reminderNoteList")
    public ResponseEntity<ResponseDTO> getReminderNotes(@RequestHeader("token") String userToken){

        List reminderNotes = noteService.getReminderNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(reminderNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/note")
    public ResponseEntity<ResponseDTO> deleteNotePermanently(@RequestBody NoteDTO noteDTO){

        String message = noteService.deleteNotePermanently(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
