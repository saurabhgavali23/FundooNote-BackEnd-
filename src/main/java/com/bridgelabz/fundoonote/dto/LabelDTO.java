package com.bridgelabz.fundoonote.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {

    public Long labelId;

    @NotNull
    @Length(min = 1, max = 255, message = "label name at least 1 char")
    public String labelName;
}
