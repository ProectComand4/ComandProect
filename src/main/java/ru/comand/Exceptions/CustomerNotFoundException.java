package ru.comand.Exceptions;

public class CustomerNotFoundException extends NullPointerException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
