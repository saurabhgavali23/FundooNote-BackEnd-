package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@Entity
@Data
@Table(name = "noteDetails")
public class NoteDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long noteId;

    public String title;
    public String description;
    public Boolean isPined;
    public Boolean isArchived;
    public Boolean isDeleted;
    public String color;
    public String reminder;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_Id", nullable = false)
    private UserDetails userDetails;

    public NoteDetails(NoteDTO noteDTO, UserDetails userDetails){
        this.createdDate = new Date();
        this.title = noteDTO.title;
        this.description = noteDTO.description;
        this.isPined = noteDTO.isPined;
        this.isArchived = noteDTO.isArchived;
        this.isDeleted = noteDTO.isDeleted;
        this.color = noteDTO.color;
        this.reminder = noteDTO.reminder;
        this.userDetails = userDetails;
    }

    public NoteDetails(NoteDTO noteDTO){
        this.noteId = noteDTO.noteId;
        this.createdDate = new Date();
        this.title = noteDTO.title;
        this.description = noteDTO.description;
        this.isPined = noteDTO.isPined;
        this.isArchived = noteDTO.isArchived;
        this.isDeleted = noteDTO.isDeleted;
        this.color = noteDTO.color;
        this.reminder = noteDTO.reminder;
    }
}
