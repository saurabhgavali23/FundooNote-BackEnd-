package com.bridgelabz.fundoonotetest.service;

import com.bridgelabz.fundoonote.dto.LabelDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.LabelDetails;
import com.bridgelabz.fundoonote.service.LabelService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LabelServiceTest {

    @Mock
    private LabelService labelServiceMock;

    @Test
    public void givenLabelDetails_whenLabelSave_shouldReturnLabelDetails() {

        LabelDetails labelDetails = new LabelDetails(new LabelDTO());

        when(labelServiceMock.addLabel(anyString(), any())).thenReturn(labelDetails);

        LabelDetails labelDetails1 = labelServiceMock.addLabel(anyString(), any());

        Assert.assertEquals(labelDetails, labelDetails1);
    }

    @Test
    public void givenLabelDetails_whenUserNotValid_thenThrowException() {

        try {
            when(labelServiceMock.addLabel(anyString(), any()))
                    .thenThrow(new FundooNoteException("User_Not_Found", HttpStatus.NOT_FOUND.value()));

            labelServiceMock.addLabel(anyString(), any());

        }catch (FundooNoteException n){

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenLabelDetails_whenLabelNotSave_thenThrowException() {

        try {
            when(labelServiceMock.addLabel(anyString(), any()))
                    .thenThrow(new FundooNoteException("Note_Not_Save", HttpStatus.NOT_IMPLEMENTED.value()));

            labelServiceMock.addLabel(anyString(), any());

        }catch (FundooNoteException n){

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenLabelDetails_whenLabelSaveInNote_shouldReturnSuccessMessage() {

        when(labelServiceMock.addLabelInNote(anyString(), anyLong(), anyLong())).thenReturn("Note_Label_Saved");

        String actualMessage = labelServiceMock.addLabelInNote(anyString(), anyLong(), anyLong());

        Assert.assertEquals("Note_Label_Saved", actualMessage);
    }

    @Test
    public void givenLabelDetails_whenNoteNotFound_thenThrowException() {

        try {
            when(labelServiceMock.addLabelInNote(anyString(), anyLong(), anyLong()))
                    .thenThrow(new FundooNoteException("Note_Not_Found", HttpStatus.NOT_FOUND.value()));

            labelServiceMock.addLabelInNote(anyString(), anyLong(), anyLong());

        }catch (FundooNoteException n){

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenLabelDetails_whenLabelNoteNotFound_thenThrowException() {

        try {
            when(labelServiceMock.addLabelInNote(anyString(), anyLong(), anyLong()))
                    .thenThrow(new FundooNoteException("Label_Not_Found", HttpStatus.NOT_FOUND.value()));

            labelServiceMock.addLabelInNote(anyString(), anyLong(), anyLong());

        }catch (FundooNoteException n){

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }

    @Test
    public void givenLabelDetails_whenLabelNoteUpdate_shouldReturnSuccessMessage() {

        when(labelServiceMock.updateLabel(anyString(), any())).thenReturn("Label_Updated_Successfully");

        String actualMessage = labelServiceMock.updateLabel(anyString(), any());

        Assert.assertEquals("Label_Updated_Successfully", actualMessage);
    }

    @Test
    public void givenLabelDetails_whenLabelNotUpdate_thenThrowException() {

        try {
            when(labelServiceMock.updateLabel(anyString(), any()))
                    .thenThrow(new FundooNoteException("Label_Not_Update", HttpStatus.NOT_IMPLEMENTED.value()));

            labelServiceMock.updateLabel(anyString(), any());

        }catch (FundooNoteException n){

            Assert.assertEquals(501, n.getHttpStatus());
        }
    }

    @Test
    public void givenUserToken_whenLabelNoteAvailable_shouldReturnLabelNoteList() {

        List<LabelDetails> labelDetailsList = new ArrayList<>();

        when(labelServiceMock.getLabelDetails(anyString())).thenReturn(labelDetailsList);

        List labelDetails = labelServiceMock.getLabelDetails(anyString());

        Assert.assertEquals(labelDetails, labelDetailsList);
    }

    @Test
    public void givenUserToken_whenLabelNoteNotAvailable_thenThrowException() {

        try {
            when(labelServiceMock.getLabelDetails(anyString()))
                    .thenThrow(new FundooNoteException("Label_Not_Found", HttpStatus.NOT_FOUND.value()));

            labelServiceMock.getLabelDetails(anyString());

        }catch (FundooNoteException n){

            Assert.assertEquals(404, n.getHttpStatus());
        }
    }
}
