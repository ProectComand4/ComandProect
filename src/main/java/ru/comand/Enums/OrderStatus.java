package ru.comand.Enums;

public enum OrderStatus {
    NEW("новый заказ"),
    PROCESSING("заказ обрабатывается"),
    COMPLETED("заказ завершён"),
    CANCELLED("заказ отменён");

    private final String rus;

    OrderStatus(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    /**
     * Определяет статус заказа по строке
     * @param rus String статус заказа
     * @return Enum статус заказа
     */
    public static OrderStatus toOrderStatus(String rus) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.name().equalsIgnoreCase(rus)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Такого статуса нет");
    }
}
