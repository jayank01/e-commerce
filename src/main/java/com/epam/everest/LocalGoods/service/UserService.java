package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.InvalidPasswordException;
import com.epam.everest.LocalGoods.Handler.UserNotFoundException;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.UserEntity;

import java.util.Optional;


public interface UserService {
    void createUser(UserDTO user);
    UserDTO getUser(String email) throws UserNotFoundException;
    void updatePassword(UserDTO userDTO, String newPassword) throws InvalidPasswordException;

}
