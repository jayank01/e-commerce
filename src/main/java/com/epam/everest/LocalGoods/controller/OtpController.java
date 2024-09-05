package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.Handler.UserNotFoundException;
import com.epam.everest.LocalGoods.dto.Message;
import com.epam.everest.LocalGoods.dto.PasswordDTO;
import com.epam.everest.LocalGoods.entity.OtpEntity;
import com.epam.everest.LocalGoods.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/verifyMail")
    public ResponseEntity<Message> verifyMail(@RequestBody PasswordDTO passwordDTO) throws UserNotFoundException {
        otpService.deleteByUsername(passwordDTO.getEmail());
        otpService.saveOTP(passwordDTO.getEmail());

        Message response = Message.builder()
                .message("Mail Sent Successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Message> verifyOTP(@RequestBody PasswordDTO passwordDTO) throws Exception {
        OtpEntity otpEntity = otpService.getOTP(passwordDTO.getEmail(), passwordDTO.getOtp());
        if (otpEntity.getExpiryTime().before(Date.from(Instant.now()))) {
            Message response = Message.builder()
                    .message("OTP Expired")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        otpService.deleteByUsername(passwordDTO.getEmail());
        Message response = Message.builder()
                .message("OTP Verified Successfully")
                .build();
        return ResponseEntity.ok(response);
    }


}
