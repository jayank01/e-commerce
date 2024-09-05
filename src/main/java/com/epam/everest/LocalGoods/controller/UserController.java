package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.dto.Message;
import com.epam.everest.LocalGoods.dto.User;
import com.epam.everest.LocalGoods.dto.UserDTO;
import com.epam.everest.LocalGoods.entity.UserEntity;
import com.epam.everest.LocalGoods.service.TokenService;
import com.epam.everest.LocalGoods.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private  final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping("/register")
    public ResponseEntity<Message> saveUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        Message response = Message.builder()
                .message(userDTO.getRole()+" Registered Successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO,HttpServletResponse response){
        Authentication authentication = this.authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                userDTO.getPassword()));

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        User user = User.builder()
                .Id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .jwtToken(this.tokenService.generateToken(authentication))
                .role(userEntity.getRole())
                .message(userEntity.getRole()+" Logged in Successfully")
                .build();


        String token = this.tokenService.generateToken(authentication);

        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
