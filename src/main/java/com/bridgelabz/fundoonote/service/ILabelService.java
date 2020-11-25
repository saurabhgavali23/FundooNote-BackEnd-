package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.module.LabelDetails;

import java.util.List;

public interface ILabelService {

    LabelDetails addLabel(String userToken, LabelDTO labelName);

    String addLabelInNote(String userToken, Long labelId, Long noteId);

    String updateLabel(String userToken, LabelDTO labelDTO);

    List getLabelDetails(String userToken);
}
