package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.OtpInvalidException;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.ForgetPasswordEntity;
import com.epam.everest.LocalGoods.entity.UserEntity;
import com.epam.everest.LocalGoods.repository.ForgetPasswordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final ForgetPasswordRepository forgetPasswordRepository;

    @Override
    public void saveOTP(UserDTO userDTO) {
        int otp = generateOTP();

        ForgetPasswordEntity forgetPasswordEntity = ForgetPasswordEntity.builder()
                .otp(otp)
                .expiryTime(new Date(System.currentTimeMillis() + 60000))
                .userEntity(objectMapper.convertValue(userDTO, UserEntity.class))
                .build();

        emailService.sendEmail(userDTO.getUsername(), "OTP for Forget Password", "Your OTP is " + otp);
        forgetPasswordRepository.save(forgetPasswordEntity);
    }

    private Integer generateOTP() {
        return (int) (Math.random() * 9000) + 1000;
    }

    @Override
    public ForgetPasswordEntity getForgetPassword(UserDTO userDTO, int otp) throws OtpInvalidException {
        return forgetPasswordRepository.findByOtpAndUserEntity(otp, objectMapper.convertValue(userDTO, UserEntity.class)).orElseThrow(() -> new OtpInvalidException("Invalid OTP"));
    }

    @Override
    public void deleteForgetPassword(UserDTO userDTO) {
        forgetPasswordRepository.deleteByUserEntity(objectMapper.convertValue(userDTO, UserEntity.class));
    }

    @Override
    public void deleteForgetPassword(ForgetPasswordEntity forgetPasswordEntity) {
        forgetPasswordRepository.delete(forgetPasswordEntity);
    }
}
