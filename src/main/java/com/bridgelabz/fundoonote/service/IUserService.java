package com.bridgelabz.fundoonote.service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.module.UserDetails;

public interface IUserService {

    UserDetails addUser(UserDTO fundooDTO);

    void forgotPassword(UserDetails email);

    String loginUser(String email, String password);
}
