package com.bridgelabz.fundoonotetest.service;

import com.bridgelabz.fundoonote.dto.UserDTO;
import com.bridgelabz.fundoonote.exception.FundooUserException;
import com.bridgelabz.fundoonote.module.UserDetails;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserService userServiceMock;

    private UserDTO userDTO;

    @Before
    public void setUp() throws Exception {
        userDTO = new UserDTO("Saurabh", "Gavali", "gavalisaurabh02@gmail.com", "Abcd@123", "advance");
    }

    @Test
    public void givenUserDetails_whenUserStore_shouldReturnStoreUserDetails() {

        when(userServiceMock.addUser(userDTO)).thenReturn("User_Added_Successfully");

        String message = userServiceMock.addUser(userDTO);

        Assert.assertEquals(message, "User_Added_Successfully");
    }

    @Test
    public void givenSameUserDetails_whenUserNotStore_thenThrowException() {
        try {
            when(userServiceMock.addUser(userDTO)).thenThrow(new FundooUserException("User_Already_Registered", HttpStatus.CONFLICT.value()));

            userServiceMock.addUser(userDTO);

        } catch (FundooUserException u) {

            Assert.assertEquals(409, u.getHttpStatus());
        }
    }

    @Test
    public void givenUserNameAndPassword_whenUserValid_shouldReturnUserDetails() {

        UserDetails userDetails = new UserDetails(userDTO);

        when(userServiceMock.loginUser("gavalisaurabh02@gmail.com", "Abcd@123")).thenReturn(userDetails);

        UserDetails userDetails1 = userServiceMock.loginUser("gavalisaurabh02@gmail.com", "Abcd@123");

        Assert.assertEquals(userDetails, userDetails1);
    }

    @Test
    public void givenInvalidEmail_whenUserEmailNotValid_thenThrowInvalidEmailException() {

        try {
            when(userServiceMock.loginUser("gavalisaurabh@gmail.com", "Abcd@123"))
                    .thenThrow(new FundooUserException("Invalid Email Id", HttpStatus.BAD_REQUEST.value()));

            userServiceMock.loginUser("gavalisaurabh@gmail.com", "Abcd@123");

        } catch (FundooUserException u) {

            Assert.assertEquals(400, u.getHttpStatus());
        }
    }

    @Test
    public void givenInvalidPassword_whenUserPasswordNotValid_thenThrowInvalidPasswordException() {

        try {
            when(userServiceMock.loginUser("gavalisaurabh02@gmail.com", "Abcd@"))
                    .thenThrow(new FundooUserException("Invalid_Password", HttpStatus.BAD_REQUEST.value()));

            userServiceMock.loginUser("gavalisaurabh02@gmail.com", "Abcd@");

        } catch (FundooUserException u) {

            Assert.assertEquals(400, u.getHttpStatus());
        }
    }

    @Test
    public void givenEmail_whenEmailIsNotVerified_thenThrowInvalidAccountException() {

        try {
            Mockito.lenient().when(userServiceMock.loginUser("gavalisaurabh10@gmail.com", "Abcd@"))
                    .thenThrow(new FundooUserException("Invalid_Account", HttpStatus.BAD_REQUEST.value()));

            userServiceMock.loginUser("gavalisaurabh02@gmail.com", "Abcd@");

        } catch (FundooUserException u) {

            Assert.assertEquals(400, u.getHttpStatus());
        }
    }

    @Test
    public void givenToken_whenTokenIsValid_shouldReturnValidMessage() {

        when(userServiceMock.verifyAccount(anyString())).thenReturn("User Verified");

        String actualMessage = userServiceMock.verifyAccount(anyString());

        Assert.assertEquals("User Verified", actualMessage);
    }

    @Test
    public void givenToken_whenTokenIsNotValid_thenThrowInvalidLinkException() {

        try {
            when(userServiceMock.verifyAccount(anyString()))
                    .thenThrow(new FundooUserException("Invalid_link", HttpStatus.BAD_REQUEST.value()));

            userServiceMock.verifyAccount(anyString());
        } catch (FundooUserException u) {

            Assert.assertEquals(400, u.getHttpStatus());
        }
    }

    @Test
    public void givenToken_whenUserTokenEmailIsNotValid_thenThrowInvalidEmailException() {

        try {
            when(userServiceMock.verifyAccount(anyString()))
                    .thenThrow(new FundooUserException("Invalid_Email", HttpStatus.BAD_REQUEST.value()));

            userServiceMock.verifyAccount(anyString());
        } catch (FundooUserException u) {

            Assert.assertEquals(400, u.getHttpStatus());
        }
    }

    @Test
    public void givenToken_whenUserTokenExpire_thenThrowLinkExpireException() {

        try {
            when(userServiceMock.verifyAccount(anyString()))
                    .thenThrow(new FundooUserException("Link_Expire", HttpStatus.BAD_REQUEST.value()));

            userServiceMock.verifyAccount(anyString());
        } catch (FundooUserException u) {

            Assert.assertEquals(400, u.getHttpStatus());
        }
    }

    @Test
    public void whenUsersAreAvailable_shouldReturnUserList(){

        List<UserDetails> userDetailsList = new ArrayList<>();

        when(userServiceMock.getAllUserRecords()).thenReturn(userDetailsList);

        List allUserRecords = userServiceMock.getAllUserRecords();

        Assert.assertEquals(userDetailsList, allUserRecords);

    }
}
