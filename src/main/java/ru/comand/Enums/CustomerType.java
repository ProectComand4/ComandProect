package ru.comand.Enums;

public enum CustomerType {
    NEW("Новый клиент"),
    REGULAR("Постоянный клиент"),
    VIP("VIP покупатель");

    private final String rus;

    CustomerType(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    /**
     * Определяет тип покупателя
     * @param choice число, соответствующее типу покупателя
     * @return тип покупателя
     */
    public static CustomerType selectCustomerType(int choice) {
        if (choice < 1 || choice > 3) {
            System.out.println("Введите число из предложенных");

        }
        return switch (choice) {
            case 1 -> NEW;
            case 2 -> REGULAR;
            case 3 -> VIP;
            default -> null;
        };
    }
    /**
     * Определяет тип покупателя по строке
     * @param rus String тип покупателя
     * @return Enum тип покупателя
     */
    public static CustomerType toCustomerType(String rus) {
        for (CustomerType type : CustomerType.values()) {
            if (type.name().equalsIgnoreCase(rus)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Такого типа покупателей нет");
    }
}
