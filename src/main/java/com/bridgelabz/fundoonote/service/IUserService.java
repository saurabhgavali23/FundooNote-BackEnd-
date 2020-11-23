package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;

import java.util.List;

public interface IUserService {

    String addUser(UserDTO fundooDTO);

    String forgotPassword(String email);

    UserDetails loginUser(String email, String password);

    String verifyAccount(String token);

    Boolean confirmPassword(String userToken);

    Boolean changePassword(UserDTO userDTO, String userToken);

    List getAllUserRecords();
}
