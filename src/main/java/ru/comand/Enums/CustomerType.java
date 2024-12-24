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
