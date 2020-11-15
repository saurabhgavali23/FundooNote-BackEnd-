package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Entity
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

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
    }
}
