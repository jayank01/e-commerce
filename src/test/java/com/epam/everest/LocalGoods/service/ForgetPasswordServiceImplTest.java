package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.OtpInvalidException;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.ForgetPasswordEntity;
import com.epam.everest.LocalGoods.entity.UserEntity;
import com.epam.everest.LocalGoods.repository.ForgetPasswordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ForgetPasswordServiceImplTest {

    @Mock
    private ForgetPasswordRepository forgetPasswordRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ForgetPasswordServiceImpl forgetPasswordService;

    private UserDTO userDTO;
    private UserEntity userEntity;
    private ForgetPasswordEntity forgetPasswordEntity;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe@example.com")
                .password("SecurePass123")
                .phoneNumber("1234567890")
                .role("USER")
                .build();

        userEntity = UserEntity.builder()
                .Id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe@example.com")
                .password("SecurePass123")
                .phoneNumber(1234567890L)
                .role("USER")
                .build();

        forgetPasswordEntity = ForgetPasswordEntity.builder()
                .Id(1L)
                .otp(1234)
                .expiryTime(Date.from(Instant.now()))
                .userEntity(userEntity)
                .build();
    }

    @Test
    void testSaveOTP() {
        when(objectMapper.convertValue(userDTO, UserEntity.class)).thenReturn(userEntity);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
        when(forgetPasswordRepository.save(any(ForgetPasswordEntity.class))).thenReturn(forgetPasswordEntity);

        forgetPasswordService.saveOTP(userDTO);

        verify(objectMapper, times(1)).convertValue(userDTO, UserEntity.class);
        verify(emailService, times(1)).sendEmail(eq(userDTO.getUsername()), eq("OTP for Forget Password"), anyString());
        verify(forgetPasswordRepository, times(1)).save(any(ForgetPasswordEntity.class));
    }

    @Test
    void testGetForgetPassword() throws OtpInvalidException {
        when(objectMapper.convertValue(userDTO, UserEntity.class)).thenReturn(userEntity);
        when(forgetPasswordRepository.findByOtpAndUserEntity(1234, userEntity)).thenReturn(Optional.of(forgetPasswordEntity));

        ForgetPasswordEntity result = forgetPasswordService.getForgetPassword(userDTO, 1234);
        assertNotNull(result);
        assertEquals(forgetPasswordEntity, result);

        verify(objectMapper, times(1)).convertValue(userDTO, UserEntity.class);
        verify(forgetPasswordRepository, times(1)).findByOtpAndUserEntity(1234, userEntity);
    }

    @Test
    void testDeleteForgetPasswordByUserDTO() {
        when(objectMapper.convertValue(userDTO, UserEntity.class)).thenReturn(userEntity);
        doNothing().when(forgetPasswordRepository).deleteByUserEntity(userEntity);

        forgetPasswordService.deleteForgetPassword(userDTO);

        verify(objectMapper, times(1)).convertValue(userDTO, UserEntity.class);
        verify(forgetPasswordRepository, times(1)).deleteByUserEntity(userEntity);
    }

    @Test
    void testDeleteForgetPasswordByEntity() {
        doNothing().when(forgetPasswordRepository).delete(forgetPasswordEntity);

        forgetPasswordService.deleteForgetPassword(forgetPasswordEntity);

        verify(forgetPasswordRepository, times(1)).delete(forgetPasswordEntity);
    }
}