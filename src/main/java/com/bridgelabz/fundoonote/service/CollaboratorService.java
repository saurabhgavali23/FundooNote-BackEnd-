package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.CollaboratorDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.CollaboratorDetails;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.CollaboratorRepository;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CollaboratorService implements ICollaboratorService {

    @Autowired
    JwtToken jwtToken;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Autowired
    NoteRepository noteRepository;

    @Override
    public CollaboratorDetails addCollaborator(String userToken, Long noteId, CollaboratorDTO collaboratorDTO) {

        long userId = Long.parseLong(jwtToken.getUserIdFromToken(userToken));

        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new FundooUserException("Invalid_User", HttpStatus.NOT_FOUND.value()));

        NoteDetails noteDetails = noteRepository.findById(noteId)
                .orElseThrow(() -> new FundooNoteException("Invalid_Note_Id", HttpStatus.NOT_FOUND.value()));

        if (!jwtToken.validateToken(userToken, userDetails.id.toString())) {

            throw new FundooUserException("Invalid_Token", HttpStatus.BAD_REQUEST.value());
        }

        CollaboratorDetails collaboratorDetails = new CollaboratorDetails(collaboratorDTO);

        CollaboratorDetails saveCollaboratorDetails = collaboratorRepository.save(collaboratorDetails);

        userDetails.getCollaboratorDetails().add(saveCollaboratorDetails);

        noteDetails.getCollaboratorDetails().add(saveCollaboratorDetails);

        userRepository.save(userDetails);

        noteRepository.save(noteDetails);

        if (saveCollaboratorDetails == null)
            throw new FundooUserException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value());

        return saveCollaboratorDetails;
    }
}
