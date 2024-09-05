package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.OtpInvalidException;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.ForgetPasswordEntity;

public interface ForgetPasswordService {
    void saveOTP(UserDTO userDTO);
    ForgetPasswordEntity getForgetPassword(UserDTO userDTO, int otp) throws OtpInvalidException;
    void deleteForgetPassword(UserDTO userDTO);
    void deleteForgetPassword(ForgetPasswordEntity forgetPasswordEntity);

}
