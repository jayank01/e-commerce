package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.Handler.InvalidPasswordException;
import com.epam.everest.LocalGoods.Handler.UserNotFoundException;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.UserEntity;
import com.epam.everest.LocalGoods.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDTO userDTO) {
        UserEntity userEntity = objectMapper.convertValue(userDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public UserDTO getUser(String email) throws UserNotFoundException {
        return objectMapper.convertValue(userRepository.findByUsername(email).orElseThrow(() -> new UserNotFoundException("User not found")), UserDTO.class);
    }

//    @Override
//    public void updatePassword(UserDTO userDTO, String newPassword) {
//        UserEntity userEntity = objectMapper.convertValue(userDTO, UserEntity.class);
//        userEntity.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(userEntity);
//    }

    @Override
    public void updatePassword(UserDTO userDTO, String newPassword) throws InvalidPasswordException {
        UserEntity userEntity = objectMapper.convertValue(userDTO, UserEntity.class);
        if (passwordEncoder.matches(newPassword, userEntity.getPassword())) {
            throw new InvalidPasswordException("New password must be different from old password");
        }
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }


}
