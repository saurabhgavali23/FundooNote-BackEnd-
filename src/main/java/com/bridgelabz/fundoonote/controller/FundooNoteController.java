package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
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
    public ResponseEntity<ResponseDTO> saveNote(@Valid @RequestParam("token") String userToken, @RequestBody NoteDTO noteDTO, BindingResult result) {

        if (result.hasErrors()) {
            throw new FundooNoteException("Invalid_Data");
        }

        long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userTokens)
                .orElseThrow(()-> new FundooNoteException("Invalid_user"));

        noteService.SaveNote(noteDTO, userDetails);

        ResponseDTO responseDTO = new ResponseDTO("Note_Added_Successfully");

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/noteList")
    public ResponseEntity<ResponseDTO> noteList(@RequestParam("token") String userToken){

        long userTokens = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userTokens)
                .orElseThrow(()-> new FundooNoteException("Invalid_User"));

        List noteList = noteService.getNoteList(userDetails);

        ResponseDTO responseDTO = new ResponseDTO(noteList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
