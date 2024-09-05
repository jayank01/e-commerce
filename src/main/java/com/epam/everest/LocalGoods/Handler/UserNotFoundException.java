package com.epam.everest.LocalGoods.Handler;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
