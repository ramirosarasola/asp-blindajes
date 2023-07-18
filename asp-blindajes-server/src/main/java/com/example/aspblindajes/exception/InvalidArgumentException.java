package com.example.aspblindajes.exception;

import lombok.AllArgsConstructor;


public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
