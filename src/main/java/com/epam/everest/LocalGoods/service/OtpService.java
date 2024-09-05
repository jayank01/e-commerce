package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.OtpInvalidException;
import com.epam.everest.LocalGoods.entity.OtpEntity;

public interface OtpService {
    void saveOTP(String username);
    OtpEntity getOTP(String email, int otp) throws OtpInvalidException;
    void deleteByUsername(String username);
}
