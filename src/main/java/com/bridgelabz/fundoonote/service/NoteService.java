package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public void SaveNote(NoteDTO noteDTO, UserDetails userDetails) {

        NoteDetails noteDetails = new NoteDetails(noteDTO, userDetails);

        NoteDetails details = noteRepository.save(noteDetails);

        if(details == null){
            throw new FundooNoteException("Note_Not_Save");
        }
    }
}
