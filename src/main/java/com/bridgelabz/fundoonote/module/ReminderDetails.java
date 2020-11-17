package com.bridgelabz.fundoonote.module;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "reminderDetails")
public class ReminderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String reminder;

    @ManyToOne
    @JoinColumn(name = "note_id")
    public NoteDetails noteDetails;

    public ReminderDetails(NoteDetails noteDetails, NoteDTO noteDTO){
        this.reminder = String.valueOf(noteDTO.reminder);
        this.noteDetails = noteDetails;
    }
}
