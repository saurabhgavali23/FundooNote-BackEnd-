package com.bridgelabz.fundoonotetest.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.service.NoteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

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
}
