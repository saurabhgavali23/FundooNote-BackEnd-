package com.bridgelabz.fundoonote.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
public class CollaboratorDTO {

    public Long collabId;

    @NotNull
    @Email
    public String emailId;
}
