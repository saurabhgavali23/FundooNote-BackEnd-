package com.bridgelabz.fundoonotetest.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.service.NoteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @Mock
    private NoteService noteServiceMock;

    private NoteDTO noteDTO;

    @Test
    public void givenNoteDetails_whenNoteSave_shouldReturnSuccessMessage(){

        when(noteServiceMock.SaveNote(any(),anyString())).thenReturn("Note_Added_Successfully");

        String message = noteServiceMock.SaveNote(any(), anyString());

        Assert.assertEquals("Note_Added_Successfully", message);
    }

    @Test
    public void givenNoteDetails_whenNoteNotSave_thenThrowException(){

        try{
            when(noteServiceMock.SaveNote(any(),anyString()))
                    .thenThrow(new FundooNoteException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.SaveNote(any(), anyString());

        }catch (FundooNoteException n){

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenNoteDetails_whenUserTokenNotValid_thenThrowException(){

        try{
            when(noteServiceMock.SaveNote(any(),anyString()))
                    .thenThrow(new FundooNoteException("Token_Not_Valid", HttpStatus.BAD_REQUEST.value()));

            noteServiceMock.SaveNote(any(), anyString());

        }catch (FundooNoteException n){

            Assert.assertEquals(400, n.getHttpStatus());
        }
    }

    @Test
    public void givenNoteDetails_whenUserIdNotValid_thenThrowException(){

        try{
            when(noteServiceMock.SaveNote(any(),anyString()))
                    .thenThrow(new FundooNoteException("Invalid_user", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.SaveNote(any(), anyString());

        }catch (FundooNoteException n){

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }
}
