package com.bridgelabz.fundoonote.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bridgelabz.fundoonote.exception.FundooUserException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class FundooUserController {

    @Value("${frontEndUrl}")
    private String url;

    @Autowired
    UserService userService;

    @PostMapping("/user")
    @ApiOperation("Api for add user")
    public ResponseEntity<ResponseDTO> newUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) throws FundooUserException {

        if (result.hasErrors()) {
            throw new FundooUserException("invalid data", HttpStatus.BAD_REQUEST.value());
        }

        String message = userService.addUser(userDTO);
        ResponseDTO userData = new ResponseDTO(message);
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/confirm-account")
    @ApiOperation("Api for confirm user account")
    public ResponseEntity<ResponseDTO> confirmAccount(@RequestParam("token") String confirmationToken) {

        userService.verifyAccount(confirmationToken);
        ResponseDTO userData = new ResponseDTO("User Verified");
        String redirectURL = url + "/successpage";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectURL));
        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers).build();
    }

    @PostMapping("/login")
    @ApiOperation("Api for login user")
    public ResponseEntity<ResponseDTO> verifyAccount(@RequestBody UserDTO userDTO) {

        UserDetails responseMessage = userService.loginUser(userDTO.email, userDTO.password);
        ResponseDTO userData = new ResponseDTO("Login Successfully", responseMessage);

        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/forgot-password")
    @ApiOperation("Api for forgot user password")
    public ResponseEntity<ResponseDTO> forgetPassword(@RequestParam("email") String email) {

        String message = userService.forgotPassword(email);

        ResponseDTO userData = new ResponseDTO(message);
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @GetMapping("/confirm-reset-password")
    @ApiOperation("Api for confirm reset password of user")
    public ResponseEntity<ResponseDTO> confirmResetPassword(@RequestParam("token") String confirmationToken) {

        Boolean isPasswordConfirmed = userService.confirmPassword(confirmationToken);
        if (isPasswordConfirmed) {
            String redirectURL = url + "/confirmpassword?token=" + confirmationToken;
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(redirectURL));
            return ResponseEntity.status(HttpStatus.FOUND)
                    .headers(headers).build();
        }
        ResponseDTO responseDTO = new ResponseDTO("Password Not Confirmed");
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password")
    @ApiOperation("Api for change user password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody UserDTO data, @RequestHeader("token") String userToken) {

        Boolean isPasswordChange = userService.changePassword(data, userToken);
        if (isPasswordChange) {
            ResponseDTO userData = new ResponseDTO("Password Changed Successfully");
            return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
        }
        ResponseDTO userData = new ResponseDTO("Password Not Changed Successfully");
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user_records")
    @ApiOperation("Api for retrieve all users")
    public ResponseEntity<ResponseDTO> getUserRecords() {

        List allUserRecords = userService.getAllUserRecords();

        ResponseDTO userList = new ResponseDTO(allUserRecords);

        return new ResponseEntity<ResponseDTO>(userList, HttpStatus.OK);
    }
}
