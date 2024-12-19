package ru.comand.Exceptions;

public class ProductNotFoundException extends NullPointerException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
