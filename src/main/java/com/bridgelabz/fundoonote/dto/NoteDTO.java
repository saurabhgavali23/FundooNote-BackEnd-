package com.bridgelabz.fundoonote.dto;

import com.bridgelabz.fundoonote.module.ReminderDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    public Long noteId;

    @NotNull
    @Length(min = 3, max = 255, message = "Invalid Title")
    public String title;

    @NotNull
    @Length(min = 3, max = 255, message = "Invalid Description")
    public String description;

    public Boolean isPined = false;

    public Boolean isArchived = false;

    public Boolean isDeleted = false;

    public String color = "#FFF";

    public List<String> reminder;
}
