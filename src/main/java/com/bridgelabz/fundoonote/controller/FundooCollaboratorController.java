package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.CollaboratorDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.module.CollaboratorDetails;
import com.bridgelabz.fundoonote.service.CollaboratorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/collaborator")
@CrossOrigin(origins = "*")
public class FundooCollaboratorController {

    @Autowired
    CollaboratorService collaboratorService;

    @PostMapping("/collaborator/{noteId}")
    @ApiOperation("Api for add collaborator")
    public ResponseEntity<ResponseDTO> addCollaborator(@Valid @RequestBody CollaboratorDTO collaboratorDTO,
                                                       @PathVariable(name = "noteId") Long noteId,
                                                       @RequestHeader("token") String userToken) {

        CollaboratorDetails collaboratorDetails = collaboratorService.addCollaborator(userToken, noteId, collaboratorDTO);

        ResponseDTO responseDTO = new ResponseDTO(collaboratorDetails);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/updateCollaborator")
    @ApiOperation("Api for update collaborator")
    public ResponseEntity<ResponseDTO> updateCollaborator(@Valid @RequestBody CollaboratorDTO collaboratorDTO,
                                                          @RequestHeader("token") String userToken) {

        String message = collaboratorService.updateCollaborator(collaboratorDTO, userToken);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
