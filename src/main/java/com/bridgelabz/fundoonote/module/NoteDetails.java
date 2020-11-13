package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import lombok.NoArgsConstructor;

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
    private String noteId;

    private String title;
    private String description;
    private Boolean isPined;
    private Boolean isArchived;
    private String color;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_Id", nullable = false)
    private UserDetails userDetails;

    public NoteDetails(NoteDTO noteDTO, UserDetails userDetails){
        this.noteId = Long.toString(UUID.randomUUID().getMostSignificantBits(), 36);
        this.createdDate = new Date();
        this.title = noteDTO.title;
        this.description = noteDTO.description;
        this.isPined = noteDTO.isPined;
        this.isArchived = noteDTO.isArchived;
        this.color = noteDTO.color;
        this.userDetails = userDetails;
    }
}
