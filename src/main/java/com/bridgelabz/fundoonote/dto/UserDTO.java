package com.bridgelabz.fundoonote.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class UserDTO {

    @Length(min = 3, max = 20, message = "Invalid FirstName")
    public String firstName;
    @Length(min = 3, max = 20, message = "Invalid LastName")
    public String lastName;
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    public String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    public String password;
    @NotNull
    public String service;

    public UserDTO(@Length(min = 3, max = 20, message = "Invalid FirstName") String firstName, @Length(min = 3, max = 20, message = "Invalid LastName") String lastName, @NotNull @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") String email, @NotNull @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") String password, @NotNull String service) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.service = service;
    }
}


