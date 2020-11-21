package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;

import java.util.List;

public interface IUserService {

    String addUser(UserDTO fundooDTO);

    void forgotPassword(String email);

    UserDetails loginUser(String email, String password);

    String verifyAccount(String token);

    void confirmPassword(String userToken);

    void changePassword(UserDTO userDTO, String userToken);

    List getAllUserRecords();
}
