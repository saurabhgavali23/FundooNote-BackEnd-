package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.service.NoteService;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/saveNote")
    @ApiOperation("Api for save the notes")
    public ResponseEntity<ResponseDTO> saveNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader("token") String userToken, BindingResult result) {

        if (result.hasErrors()) {
            throw new FundooNoteException("Invalid_Data", HttpStatus.BAD_REQUEST.value());
        }

        String responseMessage = noteService.saveNote(noteDTO, userToken);

        ResponseDTO responseDTO = new ResponseDTO(responseMessage);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/noteList")
    @ApiOperation("Api for retrieve all notes")
    public ResponseEntity<ResponseDTO> noteList(@RequestHeader("token") String userToken){

        List noteList = noteService.getNoteList(userToken);

        ResponseDTO responseDTO = new ResponseDTO(noteList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/pinUnpinNote")
    @ApiOperation("Api for pin or unpin note")
    public ResponseEntity<ResponseDTO> updatePin(@RequestBody NoteDTO noteDTO){

        String message = noteService.updatePin(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/archiveNote")
    @ApiOperation("Api for archive or un-archive note")
    public ResponseEntity<ResponseDTO> updateArchive(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateArchived(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PostMapping("/trashNote")
    @ApiOperation("Api for trash or restore note")
    public ResponseEntity<ResponseDTO> updateTrash(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateTrash(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/colorNote")
    @ApiOperation("Api for update color of note")
    public ResponseEntity<ResponseDTO> updateColor(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateColor(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/titleAndDescriptionNote")
    @ApiOperation("Api for update title and description of note")
    public ResponseEntity<ResponseDTO> updateTitleAndDescription(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateTitleAndDescription(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/reminderNote")
    @ApiOperation("Api for update reminder of note")
    public ResponseEntity<ResponseDTO> updateReminder(@RequestBody NoteDTO noteDTO){

        String message = noteService.updateReminder(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/pinNoteList")
    @ApiOperation("Api for retrieve all pin notes")
    public ResponseEntity<ResponseDTO> getPinNote(@RequestHeader("token") String userToken){

        List pinNote = noteService.getPinNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(pinNote);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/archiveNoteList")
    @ApiOperation("Api for retrieve all archive notes")
    public ResponseEntity<ResponseDTO> getArchiveNotes(@RequestHeader("token") String userToken){

        List archiveNotes = noteService.getArchiveNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(archiveNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/trashNoteList")
    @ApiOperation("Api for retrieve all trash notes")
    public ResponseEntity<ResponseDTO> getTrashNotes(@RequestHeader("token") String userToken){

        List trashNotes = noteService.getTrashNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(trashNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/reminderNoteList")
    @ApiOperation("Api for retrieve all reminder notes")
    public ResponseEntity<ResponseDTO> getReminderNotes(@RequestHeader("token") String userToken){

        List reminderNotes = noteService.getReminderNotes(userToken);

        ResponseDTO responseDTO = new ResponseDTO(reminderNotes);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/note")
    @ApiOperation("Api for permanently delete note")
    public ResponseEntity<ResponseDTO> deleteNotePermanently(@RequestBody NoteDTO noteDTO){

        String message = noteService.deleteNotePermanently(noteDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/searchNote")
    @ApiOperation("Api for search note base on title and description")
    public ResponseEntity<ResponseDTO> searchNote(@RequestHeader("token") String userToken,
                                                  @RequestParam("word") String word){

        List searchNoteList = noteService.searchNote(userToken, word);

        ResponseDTO responseDTO = new ResponseDTO(searchNoteList);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
