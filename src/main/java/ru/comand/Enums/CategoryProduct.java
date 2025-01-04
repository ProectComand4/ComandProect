package ru.comand.Enums;

public enum CategoryProduct {
    FOOD("ПИЩА"),
    ELECTRONICS("ЭЛЕКТРОНИКА"),
    CLOTHING("ОДЕЖДА");
    private final String rus;

    CategoryProduct(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }
    /**
     * Определяет тип продукта по строке
     * @param rus String тип продукта
     * @return Enum тип продукта
     */
    public static CategoryProduct toCategoryRus(String rus) {
        for (CategoryProduct type : CategoryProduct.values()) {
            if (type.name().equalsIgnoreCase(rus)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Такой категории нет");
    }


}
