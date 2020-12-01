package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.CollaboratorDTO;
import com.bridgelabz.fundoonote.module.CollaboratorDetails;

import java.util.List;

public interface ICollaboratorService {

    CollaboratorDetails addCollaborator(String userToken, Long noteId, CollaboratorDTO collaboratorDTO);

    String updateCollaborator(CollaboratorDTO collaboratorDTO, String userToken);

    List<CollaboratorDetails> getCollaboratorList(String userToken);

    String deleteCollaborator(String userToken, CollaboratorDTO collaboratorDTO);
}
