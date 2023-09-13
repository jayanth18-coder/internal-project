package com.alibou.videocall.user;

public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException(String message) {
        super(message);
    }
}