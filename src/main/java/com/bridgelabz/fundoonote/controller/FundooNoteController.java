package com.bridgelabz.fundoonote.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.dto.ResponseDTO;
import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.exception.FundooNoteException;
import com.bridgelabz.fundoonote.module.ConfirmationToken;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.ConfirmationTokenRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.EmailSenderService;
import com.bridgelabz.fundoonote.service.UserService;

@RestController
@RequestMapping("/fundoonote")
@CrossOrigin(origins = "*")
public class FundooNoteController {

    @Autowired
    UserService userService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    EmailSenderService emailSenderService;


    @PostMapping("/user")
    public ResponseEntity<ResponseDTO> newUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

        if (result.hasErrors()) {
            throw new FundooNoteException(FundooNoteException.ExceptionType.INVALID_DATA, "invalid data");
        }
        Optional<UserDetails> byEmailId = userRepository.findByEmail(userDTO.email);

        if (byEmailId.isPresent()) {
            throw new FundooNoteException(FundooNoteException.ExceptionType.USER_ALREADY_REGISTERED,
                    "User_Already_Registered");
        }
        UserDetails userDetails = userService.addUser(userDTO);
        ResponseDTO userData = new ResponseDTO("User Added Successfully", userDetails);
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<ResponseDTO> confirmAccount(@RequestParam("token") String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.LINK_IS_INVALID, "Invlid_link"));

        UserDetails user = userRepository.findByEmail(token.user.email)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL, "Invalid_Email"));

        if (token.isExpired())
            throw new FundooNoteException(FundooNoteException.ExceptionType.LINK_IS_EXPIRED, "Link_Expired");

        user.setVerified(true);
        userRepository.save(user);
        ResponseDTO userData = new ResponseDTO("User Verified", user);
        String redirectURL = "http://localhost:3000/successpage";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectURL));
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers).build();
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> verifyAccount(@Valid @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {

        String responseMessage = userService.loginUser(email, password);
        ResponseDTO userData = new ResponseDTO(responseMessage);

        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgetpassword(@RequestParam("email") String email) {


        UserDetails findByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL, "Invalid Email Id"));

        userService.forgotPassword(findByEmail);

        ResponseDTO userData = new ResponseDTO("Password Reset Link Sent to Email");
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/confirm-reset-password")
    public ResponseEntity<ResponseDTO> confirmResetPassword(@RequestParam("token") String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.LINK_IS_INVALID, "Invlid_link"));

        UserDetails user = userRepository.findByEmail(token.user.email)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL, "Invalid_Email"));
        user.setVerified(true);
        userRepository.save(user);
        String redirectURL = "http://localhost:3000/confirmpassword?token=" + token.confirmationToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectURL));
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody UserDTO data, @RequestParam("token") String userToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(userToken)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_USER, "Invlid_User"));

        UserDetails user = userRepository.findByEmail(token.user.email)
                .orElseThrow(() -> new FundooNoteException(FundooNoteException.ExceptionType.INVALID_EMAIL, "Invalid_Email"));

        String newPassword = encoder.encode(data.password);
        user.setPassword(newPassword);
        userRepository.save(user);

        ResponseDTO userData = new ResponseDTO("Password Changed Successfully", user);

        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }
}
