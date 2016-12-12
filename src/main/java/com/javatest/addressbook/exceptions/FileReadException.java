package com.javatest.addressbook.exceptions;



public class FileReadException extends RuntimeException {
    public FileReadException(Exception e) {
        super("Error reading from file", e);
    }
}
