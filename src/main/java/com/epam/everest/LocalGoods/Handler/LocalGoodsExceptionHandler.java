package com.epam.everest.LocalGoods.Handler;

import com.epam.everest.LocalGoods.dto.Message;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class LocalGoodsExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Message> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Message errors = Message.builder()
                .message(ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", ")))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Message> constraintViolationException(SQLIntegrityConstraintViolationException ex) {
        Message response = Message.builder()
                .message("Creditionals already exists")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public  ResponseEntity<Message> userNotFoundException(UserNotFoundException ex){
        Message response = Message.builder()
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(OtpInvalidException.class)
    public ResponseEntity<Message> otpException(OtpInvalidException ex) {
        Message response = Message.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleAuthenticationException(Exception e, HttpServletResponse response) {
        // Handle login failure
        Cookie statusCookie = new Cookie("status", URLEncoder.encode("login unsuccessful", StandardCharsets.UTF_8));
        statusCookie.setPath("/");
        response.addCookie(statusCookie);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Login unsuccessful: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Message> handleInvalidPasswordException(InvalidPasswordException ex) {
        Message response = Message.builder()
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<Message> handleCategoryException(CategoryException ex) {
        Message response = Message.builder()
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleProductNotFoundException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}

