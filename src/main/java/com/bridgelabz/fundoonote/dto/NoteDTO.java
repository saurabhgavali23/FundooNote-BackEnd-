package com.bridgelabz.fundoonote.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    @NotNull
    @Length(min = 3, max = 255, message = "Invalid Title")
    public String title;

    @NotNull
    @Length(min = 3, max = 255, message = "Invalid Description")
    public String description;

    public Boolean isPined;

    public Boolean isArchived;

    public String color;
}
