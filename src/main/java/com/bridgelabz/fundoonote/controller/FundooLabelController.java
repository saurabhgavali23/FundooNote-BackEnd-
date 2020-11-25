package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin(origins = "*")
public class FundooLabelController {

    @Autowired
    NoteService noteService;

    @PostMapping("/label")
    public ResponseEntity<ResponseDTO> addLabels(@RequestHeader("token") String userToken, @RequestBody LabelDTO labelDTO){

        LabelDetails responseLabelDetails = noteService.addLabel(userToken, labelDTO);

        ResponseDTO responseDTO = new ResponseDTO(responseLabelDetails);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/labelNote/{labelId}/{noteId}")
    public ResponseEntity<ResponseDTO> addLabelInNote(@RequestHeader("token") String userToken, @PathVariable(name = "labelId") Long labelId,
                                                      @PathVariable(name = "noteId") Long noteId){

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
