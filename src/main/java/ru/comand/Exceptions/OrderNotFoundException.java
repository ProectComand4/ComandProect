package ru.comand.Exceptions;

public class OrderNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Заказ с id - %d не найден";

    public OrderNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }
}
