package com.bideafactory.bookingApp.exception;


import lombok.Getter;

@Getter
public class BookingException extends RuntimeException {
    private final int statusCode;
    private final String error;

    public BookingException(int statusCode, String error, String message) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
    }

}