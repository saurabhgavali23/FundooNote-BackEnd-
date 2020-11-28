package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.CollaboratorDTO;
import com.bridgelabz.fundoonote.module.CollaboratorDetails;

public interface ICollaboratorService {

    CollaboratorDetails addCollaborator(String userToken, Long noteId, CollaboratorDTO collaboratorDTO);

    String updateCollaborator(CollaboratorDTO collaboratorDTO, String userToken);
}
