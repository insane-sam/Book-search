package com.example.demo.exception;

public class NoBookFoundBetweenPriceException extends RuntimeException {
    public NoBookFoundBetweenPriceException(String message) {
        super(message);
    }

}
