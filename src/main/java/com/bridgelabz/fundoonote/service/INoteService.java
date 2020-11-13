package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.module.UserDetails;

public interface INoteService {

    public void SaveNote(NoteDTO noteDTO, UserDetails userDetails);
}
