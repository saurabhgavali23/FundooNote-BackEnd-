package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @JsonIgnore
    @Transient
    LocalDateTime now = LocalDateTime.now();

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    public LocalDateTime modifiedDate = now;

    public NoteDetails(NoteDTO noteDTO){
        this.createdDate = this.now;
        this.title = noteDTO.title;
        this.description = noteDTO.description;
        this.isPined = noteDTO.isPined;
        this.isArchived = noteDTO.isArchived;
        this.isDeleted = noteDTO.isDeleted;
        this.color = noteDTO.color;
        this.reminder = noteDTO.reminder;
    }
}
