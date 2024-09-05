package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.OtpInvalidException;
import com.epam.everest.LocalGoods.entity.OtpEntity;
import com.epam.everest.LocalGoods.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService{

    private final OtpRepository otpRepository;
    private final EmailService emailService;

    @Override
    public void saveOTP(String username) {
        int otp = generateOTP();
        OtpEntity otpEntity = OtpEntity.builder()
                .otp(otp)
                .expiryTime(new Date(System.currentTimeMillis() + 60000))
                .username(username)
                .build();

        emailService.sendEmail(username, "OTP for Successfully Registration", "Your OTP is " + otp);
        otpRepository.save(otpEntity);
    }

    private Integer generateOTP() {
        return (int) (Math.random() * 9000) + 1000;
    }

    @Override
    public OtpEntity getOTP(String email, int otp) throws OtpInvalidException {
        return otpRepository.findByOtpAndUsername(otp, email).orElseThrow(() -> new OtpInvalidException("Invalid OTP"));
    }

    @Override
    public void deleteByUsername(String username) {
        otpRepository.deleteByUsername(username);
    }
}
