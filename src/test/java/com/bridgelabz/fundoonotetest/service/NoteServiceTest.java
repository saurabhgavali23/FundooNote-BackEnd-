package com.bridgelabz.fundoonotetest.service;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.NoteDetails;
import com.bridgelabz.fundoonote.service.NoteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @Mock
    private NoteService noteServiceMock;

    private NoteDTO noteDTO;

    @Test
    public void givenNoteDetails_whenNoteSave_shouldReturnSuccessMessage() {

        when(noteServiceMock.saveNote(any(), anyString())).thenReturn("Note_Added_Successfully");

        String message = noteServiceMock.saveNote(any(), anyString());

        Assert.assertEquals("Note_Added_Successfully", message);
    }

    @Test
    public void givenNoteDetails_whenNoteNotSave_thenThrowException() {

        try {
            when(noteServiceMock.saveNote(any(), anyString()))
                    .thenThrow(new FundooNoteException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.saveNote(any(), anyString());

        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenNoteDetails_whenUserTokenNotValid_thenThrowException() {

        try {
            when(noteServiceMock.saveNote(any(), anyString()))
                    .thenThrow(new FundooNoteException("Token_Not_Valid", HttpStatus.BAD_REQUEST.value()));

            noteServiceMock.saveNote(any(), anyString());

        } catch (FundooNoteException n) {

            Assert.assertEquals(400, n.getHttpStatus());
        }
    }

    @Test
    public void givenNoteDetails_whenUserIdNotValid_thenThrowException() {

        try {
            when(noteServiceMock.saveNote(any(), anyString()))
                    .thenThrow(new FundooNoteException("Invalid_user", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.saveNote(any(), anyString());

        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenUserToken_whenUserValid_shouldReturnNoteList() {

        List<NoteDetails> noteDetailsList = new ArrayList<>();

        when(noteServiceMock.getNoteList(anyString())).thenReturn(noteDetailsList);

        List noteList = noteServiceMock.getNoteList(anyString());

        Assert.assertEquals(noteDetailsList, noteList);
    }

    @Test
    public void givenUserToken_whenNotesNotAvailable_thenThrowException() {

        try {
            when(noteServiceMock.getNoteList(anyString()))
                    .thenThrow(new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.getNoteList(anyString());
        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenPinNoteDetails_whenPinNoteUpdate_shouldReturnSuccessMessage() {

        when(noteServiceMock.updatePin(any())).thenReturn("PinNote Updated");

        String actualMessage = noteServiceMock.updatePin(any());

        Assert.assertEquals("PinNote Updated", actualMessage);
    }

    @Test
    public void givenPinNoteDetails_whenPinNoteNotUpdate_theThrowException() {

        try {
            when(noteServiceMock.updatePin(any()))
                    .thenThrow(new FundooNoteException("PinNote Not Update", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.updatePin(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenPinNoteDetails_whenPinNoteIdNotFound_theThrowException() {

        try {
            when(noteServiceMock.updatePin(any()))
                    .thenThrow(new FundooNoteException("Note Not Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.updatePin(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenArchiveNoteDetails_whenArchiveNoteUpdate_shouldReturnSuccessMessage() {

        when(noteServiceMock.updateArchived(any())).thenReturn("ArchiveNote Updated");

        String actualMessage = noteServiceMock.updateArchived(any());

        Assert.assertEquals("ArchiveNote Updated", actualMessage);
    }

    @Test
    public void givenArchiveNoteDetails_whenArchiveNoteNotUpdate_thenThrowException() {

        try {
            when(noteServiceMock.updateArchived(any()))
                    .thenThrow(new FundooNoteException("ArchiveNote Not Update", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.updateArchived(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenArchiveNoteDetails_whenArchiveNoteIdNotFound_thenThrowException() {

        try {
            when(noteServiceMock.updateArchived(any()))
                    .thenThrow(new FundooNoteException("Note Not Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.updateArchived(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenTrashNoteDetails_whenTrashNoteUpdate_shouldReturnSuccessMessage() {

        when(noteServiceMock.updateTrash(any())).thenReturn("TrashNote Updated");

        String actualMessage = noteServiceMock.updateTrash(any());

        Assert.assertEquals("TrashNote Updated", actualMessage);
    }

    @Test
    public void givenTrashNoteDetails_whenTrashNoteNotUpdate_thenThrowException() {

        try {
            when(noteServiceMock.updateTrash(any()))
                    .thenThrow(new FundooNoteException("TrashNote Not Update", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.updateTrash(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenTrashNoteDetails_whenTrashNoteIdNotFound_thenThrowException() {

        try {
            when(noteServiceMock.updateTrash(any()))
                    .thenThrow(new FundooNoteException("Note Not Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.updateTrash(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenColorNoteDetails_whenColorNoteUpdate_shouldReturnSuccessMessage() {

        when(noteServiceMock.updateColor(any())).thenReturn("ColorNote Updated");

        String actualMessage = noteServiceMock.updateColor(any());

        Assert.assertEquals("ColorNote Updated", actualMessage);
    }

    @Test
    public void givenColorNoteDetails_whenColorNoteNotUpdate_thenThrowException() {

        try {
            when(noteServiceMock.updateColor(any()))
                    .thenThrow(new FundooNoteException("ColorNote Not Update", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.updateColor(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenColorNoteDetails_whenColorNoteIdNotFound_thenThrowException() {

        try {
            when(noteServiceMock.updateColor(any()))
                    .thenThrow(new FundooNoteException("Note Not Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.updateColor(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenNoteDetails_whenTitleAndDescriptionUpdate_shouldReturnSuccessMessage() {

        when(noteServiceMock.updateTitleAndDescription(any())).thenReturn("TitleAndDescriptionNote Updated");

        String actualMessage = noteServiceMock.updateTitleAndDescription(any());

        Assert.assertEquals("TitleAndDescriptionNote Updated", actualMessage);
    }

    @Test
    public void givenNoteDetails_whenTitleAndDescriptionNoteNotUpdate_thenThrowException() {

        try {
            when(noteServiceMock.updateTitleAndDescription(any()))
                    .thenThrow(new FundooNoteException("TitleAndDescriptionNote Not Update", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.updateTitleAndDescription(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenNoteDetails_whenTitleAndDescriptionNoteIdNotFound_thenThrowException() {

        try {
            when(noteServiceMock.updateTitleAndDescription(any()))
                    .thenThrow(new FundooNoteException("Note Not Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.updateTitleAndDescription(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenReminderNoteDetails_whenReminderNoteUpdate_shouldReturnSuccessMessage() {

        when(noteServiceMock.updateReminder(any())).thenReturn("ReminderNote Updated");

        String actualMessage = noteServiceMock.updateReminder(any());

        Assert.assertEquals("ReminderNote Updated", actualMessage);
    }

    @Test
    public void givenReminderNoteDetails_whenReminderNoteUpdate_thenThrowException() {

        try {
            when(noteServiceMock.updateReminder(any()))
                    .thenThrow(new FundooNoteException("ReminderNote Not Update", HttpStatus.NOT_IMPLEMENTED.value()));

            noteServiceMock.updateReminder(any());
        } catch (FundooNoteException n) {

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenUserToken_whenPinNoteFound_shouldReturnPinNoteList() {

        List<NoteDetails> noteDetailsList = new ArrayList<>();

        when(noteServiceMock.getPinNotes(anyString())).thenReturn(noteDetailsList);

        List pinNotesLis = noteServiceMock.getPinNotes(anyString());

        Assert.assertEquals(noteDetailsList, pinNotesLis);
    }

    @Test
    public void givenUserToken_whenPinNoteNotFound_thenThrowNotFoundException() {

        try {
            when(noteServiceMock.getPinNotes(anyString())).thenThrow(new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value()));

            noteServiceMock.getPinNotes(anyString());

        } catch (FundooNoteException n) {

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }
}
