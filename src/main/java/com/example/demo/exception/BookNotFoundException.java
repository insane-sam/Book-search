package com.example.demo.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        // System.out.println("hello");
        super(message);
    }
}
