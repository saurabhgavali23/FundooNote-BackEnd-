package com.bridgelabz.fundoonote.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Component
public class UserDTO {

    @Length(min = 3, max = 20, message = "Invalid FirstName")
    public String firstName;
    @Length(min = 3, max = 20, message = "Invalid LastName")
    public String lastName;
    public String gender;
    @Pattern(regexp = "\\d{10}")
    public String phoneNumber;
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    public String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    public String password;

    public UserDTO(@Length(min = 3, max = 20, message = "Invalid FirstName") String firstName, @Length(min = 3, max = 20, message = "Invalid LastName") String lastName, String gender, @Pattern(regexp = "\\d{10}") String phoneNumber, @NotNull @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$") String email, @NotNull @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }
}


