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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void givenUserDetails_whenUserStore_shouldReturnStoreUserDetails(){
        when(userServiceMock.addUser(userDTO)).thenReturn("User_Added_Successfully");
        String message = userServiceMock.addUser(userDTO);
        Assert.assertEquals(message, "User_Added_Successfully");
    }

    @Test
    public void givenSameUserDetails_whenUserNotStore_thenThrowException(){
        try{
            when(userServiceMock.addUser(userDTO)).thenThrow(new FundooUserException("User_Already_Registered", HttpStatus.CONFLICT.value()));
            userServiceMock.addUser(userDTO);
        }catch (FundooUserException u){
            Assert.assertEquals(409, u.getHttpStatus());
        }
    }
}
