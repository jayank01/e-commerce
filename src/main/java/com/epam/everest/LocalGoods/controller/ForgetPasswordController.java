package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.Handler.UserNotFoundException;
import com.epam.everest.LocalGoods.dto.Message;
import com.epam.everest.LocalGoods.dto.PasswordDTO;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.ForgetPasswordEntity;
import com.epam.everest.LocalGoods.service.ForgetPasswordService;
import com.epam.everest.LocalGoods.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forget-password")
@CrossOrigin(origins = "*")
public class ForgetPasswordController {

    private final UserService userService;
    private final ForgetPasswordService forgetPasswordService;

    @PostMapping("/verifyMail")
    public ResponseEntity<Message> verifyMail(@RequestBody PasswordDTO passwordDTO) throws UserNotFoundException {
        UserDTO userDTO = userService.getUser(passwordDTO.getEmail());
        forgetPasswordService.deleteForgetPassword(userDTO);
        forgetPasswordService.saveOTP(userDTO);

        Message response = Message.builder()
                .message("Mail Sent Successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Message> verifyOTP(@RequestBody PasswordDTO passwordDTO) throws Exception {
        UserDTO userDTO = userService.getUser(passwordDTO.getEmail());
        ForgetPasswordEntity forgetPasswordEntity = forgetPasswordService.getForgetPassword(userDTO, passwordDTO.getOtp());

        if (forgetPasswordEntity.getExpiryTime().before(Date.from(Instant.now()))) {
            Message response = Message.builder()
                    .message("OTP Expired")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        forgetPasswordService.deleteForgetPassword(forgetPasswordEntity);
        Message response = Message.builder()
                .message("OTP Verified Successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Message> changePassword(@Valid @RequestBody PasswordDTO passwordDTO) throws Exception{
        if(!passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            Message response = Message.builder()
                    .message("Passwords do not match")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        UserDTO userDTO = userService.getUser(passwordDTO.getEmail());
        userService.updatePassword(userDTO, passwordDTO.getPassword());
        Message response = Message.builder()
                .message("Password Updated Successfully")
                .build();
        return ResponseEntity.ok(response);
    }

}
