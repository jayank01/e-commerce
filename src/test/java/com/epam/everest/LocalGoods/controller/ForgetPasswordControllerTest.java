package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.Handler.UserNotFoundException;
import com.epam.everest.LocalGoods.dto.Message;
import com.epam.everest.LocalGoods.dto.PasswordDTO;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.ForgetPasswordEntity;
import com.epam.everest.LocalGoods.service.ForgetPasswordService;
import com.epam.everest.LocalGoods.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ForgetPasswordControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ForgetPasswordService forgetPasswordService;

    @InjectMocks
    private ForgetPasswordController forgetPasswordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyMail() throws UserNotFoundException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test@example.com");

        when(userService.getUser(anyString())).thenReturn(userDTO);
        doNothing().when(forgetPasswordService).deleteForgetPassword(any(UserDTO.class));
        doNothing().when(forgetPasswordService).saveOTP(any(UserDTO.class));

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setEmail("test@example.com");

        ResponseEntity<Message> response = forgetPasswordController.verifyMail(passwordDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mail Sent Successfully", response.getBody().getMessage());
    }

    @Test
    void testVerifyOTP() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test@example.com");

        ForgetPasswordEntity forgetPasswordEntity = new ForgetPasswordEntity();
        forgetPasswordEntity.setExpiryTime(new Date(System.currentTimeMillis() + 60000));

        when(userService.getUser(anyString())).thenReturn(userDTO);
        when(forgetPasswordService.getForgetPassword(any(UserDTO.class), anyInt())).thenReturn(forgetPasswordEntity);
        doNothing().when(forgetPasswordService).deleteForgetPassword(any(ForgetPasswordEntity.class));

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setEmail("test@example.com");
        passwordDTO.setOtp(1234);

        ResponseEntity<Message> response = forgetPasswordController.verifyOTP(passwordDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OTP Verified Successfully", response.getBody().getMessage());
    }

    @Test
    void testChangePassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test@example.com");

        when(userService.getUser(anyString())).thenReturn(userDTO);
        doNothing().when(userService).updatePassword(any(UserDTO.class), anyString());

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setEmail("test@example.com");
        passwordDTO.setPassword("newPassword");
        passwordDTO.setConfirmPassword("newPassword");

        ResponseEntity<Message> response = forgetPasswordController.changePassword(passwordDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password Updated Successfully", response.getBody().getMessage());
    }
}