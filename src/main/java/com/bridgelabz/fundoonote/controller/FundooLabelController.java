package com.bridgelabz.fundoonote.controller;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.service.LabelService;
import io.swagger.annotations.ApiOperation;
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
    LabelService labelService;

    @PostMapping("/label")
    @ApiOperation("Api for save label")
    public ResponseEntity<ResponseDTO> addLabels(@RequestHeader("token") String userToken, @RequestBody LabelDTO labelDTO){

        LabelDetails responseLabelDetails = labelService.addLabel(userToken, labelDTO);

        ResponseDTO responseDTO = new ResponseDTO(responseLabelDetails);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/labelNote/{labelId}/{noteId}")
    @ApiOperation("Api for add label with note")
    public ResponseEntity<ResponseDTO> addLabelInNote(@RequestHeader("token") String userToken, @PathVariable(name = "labelId") Long labelId,
                                                      @PathVariable(name = "noteId") Long noteId){

        String message = labelService.addLabelInNote(userToken, labelId, noteId);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/updateLabel")
    @ApiOperation("Api for update label")
    public ResponseEntity<ResponseDTO> updateLabel(@RequestHeader("token") String userToken,
                                                   @RequestBody LabelDTO labelDTO){

        String message = labelService.updateLabel(userToken, labelDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/labelList")
    @ApiOperation("Api for retrieve all labels")
    public ResponseEntity<ResponseDTO> getLabelDetails(@RequestHeader("token") String userToken){

        List labelDetails = labelService.getLabelDetails(userToken);

        ResponseDTO responseDTO = new ResponseDTO(labelDetails);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/label")
    @ApiOperation("Api for delete label")
    public ResponseEntity<ResponseDTO> deleteLabel(@RequestHeader("token") String userToken,
                                                   @RequestBody LabelDTO labelDTO){

        String message = labelService.deleteLabel(userToken, labelDTO);

        ResponseDTO responseDTO = new ResponseDTO(message);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
