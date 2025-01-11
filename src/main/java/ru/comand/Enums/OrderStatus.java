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
     * Меняет статус заказа
     * @param choice число, соответствующее статусу заказа
     * @return статус заказа
     */
    public static OrderStatus changeOrderStatus(int choice) {
        if (choice < 1 || choice > 3) {
            System.out.println("Введите число из предложенных");

        }
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.ordinal() == choice) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Заказов с таким статусом нет");
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
