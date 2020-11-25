package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.LabelDTO;
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

@Service
public class LabelService implements ILabelService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtToken jwtToken;

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    NoteRepository noteRepository;

    @Override
    public LabelDetails addLabel(String userToken, LabelDTO labelDTO) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

        LabelDetails labelDetails = new LabelDetails(labelDTO);

        LabelDetails saveLabelDetails = labelRepository.save(labelDetails);

        userDetails.getLabelDetails().add(saveLabelDetails);
        userRepository.save(userDetails);

        if (saveLabelDetails == null) {
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

        if (i == 0) {
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

        if (labelList.isEmpty())
            throw new FundooNoteException("Label_Not_Found", HttpStatus.NOT_FOUND.value());

        return labelList;
    }
}
