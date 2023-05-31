package com.example.project1.services.handlers;

public interface Handler<T> {
    void setNext(Handler handler);
    T handle(Object req);
}
