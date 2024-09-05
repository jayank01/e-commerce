package com.epam.everest.LocalGoods.Handler;

public class    InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}