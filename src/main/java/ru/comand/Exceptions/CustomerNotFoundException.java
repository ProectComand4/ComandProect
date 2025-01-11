package ru.comand.Exceptions;

public class CustomerNotFoundException extends NullPointerException {

    private static final String MESSAGE = "Покупатель с id - %d не найден";

    public CustomerNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }
}
