package org.example.exception;

public class PhoneAlreadyUsedException extends Exception{

    public PhoneAlreadyUsedException(String message) {
        super(message);
    }
}
