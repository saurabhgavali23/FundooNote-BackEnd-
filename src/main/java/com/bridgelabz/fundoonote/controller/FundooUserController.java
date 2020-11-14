package com.bridgelabz.fundoonote.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bridgelabz.fundoonote.exception.UserAlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.UserService;

@RestController
@RequestMapping("/fundoonote")
@CrossOrigin(origins = "*")
public class FundooUserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/user")
    public ResponseEntity<ResponseDTO> newUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

        if (result.hasErrors()) {
            throw new FundooNoteException("invalid data");
        }
        Optional<UserDetails> byEmailId = userRepository.findByEmail(userDTO.email);

        if (byEmailId.isPresent()) {
            throw new UserAlreadyPresentException("User_Already_Registered");
        }
        String message = userService.addUser(userDTO);
        ResponseDTO userData = new ResponseDTO(message);
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<ResponseDTO> confirmAccount(@RequestParam("token") String confirmationToken) {

        userService.verifyAccount(confirmationToken);
        ResponseDTO userData = new ResponseDTO("User Verified");
        String redirectURL = "http://localhost:3000/successpage";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectURL));
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> verifyAccount(@RequestBody UserDTO userDTO) {

        UserDetails responseMessage = userService.loginUser(userDTO.email, userDTO.password);
        ResponseDTO userData = new ResponseDTO("Login Successfully",responseMessage);

        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgetPassword(@RequestHeader("email") String email) {


        UserDetails findByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new FundooNoteException("Invalid Email Id"));

        userService.forgotPassword(findByEmail);

        ResponseDTO userData = new ResponseDTO("Password Reset Link Sent to Email");
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/confirm-reset-password")
    public ResponseEntity<ResponseDTO> confirmResetPassword(@RequestHeader("token") String confirmationToken) {

        userService.confirmPassword(confirmationToken);
        String redirectURL = "http://localhost:3000/confirmpassword?token=" + confirmationToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectURL));
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody UserDTO data, @RequestHeader("token") String userToken) {

        userService.changePassword(data, userToken);
        ResponseDTO userData = new ResponseDTO("Password Changed Successfully");

        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/user_records")
    public ResponseEntity<ResponseDTO> getUserRecords(){

        List allUserRecords = userService.getAllUserRecords();

        ResponseDTO userList = new ResponseDTO(allUserRecords);

        return new ResponseEntity<ResponseDTO>(userList, HttpStatus.OK);
    }
}
