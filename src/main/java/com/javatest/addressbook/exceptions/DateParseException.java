package com.javatest.addressbook.exceptions;

public class DateParseException extends RuntimeException {
    public DateParseException(Exception e) {
        super("Invalid date format", e);
    }
}
