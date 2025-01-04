package ru.comand.Enums;

public enum OrderStatus {

    NEW("Новый заказ"),
    PROCESSING("Обработка"),
    COMPLETED("Завершенный"),
    CANCELLED("Отмененный");

    private final String rus;

    OrderStatus(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    /**
     * Определяет статус заказа
     * @param choice число, соответствующее статус заказа
     * @return тип покупателя
     */
    public static OrderStatus selectOrderStatus(int choice) {
        if (choice < 1 || choice > 4) {
            System.out.println("Введите число из предложенных");

        }
        return switch (choice) {
            case 1 -> NEW;
            case 2 -> PROCESSING;
            case 3 -> COMPLETED;
            case 4 -> CANCELLED;
            default -> null;
        };
    }

    /**
     * Определяет статус заказа по строке
     * @param rus String статус заказа
     * @return Enum статус заказа
     */
    public static OrderStatus toOrderStatus(String rus) {
        for (OrderStatus type : OrderStatus.values()) {
            if (type.name().equalsIgnoreCase(rus)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Такого типа покупателей нет");
    }

}
