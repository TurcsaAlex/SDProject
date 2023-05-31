package com.example.project1.model.exceptions.service;

public class MyError extends RuntimeException{
    public MyError(String message) {
        super(message);
    }
}
