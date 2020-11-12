package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;

public interface IUserService {

    String addUser(UserDTO fundooDTO);

    void forgotPassword(UserDetails email);

    UserDetails loginUser(String email, String password);

    String verifyAccount(String token);

    void confirmPassword(String userToken);
}
