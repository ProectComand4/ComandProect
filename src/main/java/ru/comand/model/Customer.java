package ru.comand.model;

import ru.comand.Enums.CustomerType;

import java.util.Objects;

public class Customer {
    private Integer id;
    private String name;
    private CustomerType type;

    public Customer(String name, CustomerType type) {
        this.id = null;
        this.name = name;
        this.type = type;
    }

    public Customer(String customerFromFile) {
        String[] parts = customerFromFile.split(";");
        this.id = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.type = CustomerType.toCustomerType(parts[2]);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && type == customer.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }

    @Override
    public String toString() {
        return "\nid - " + id + ", имя покупателя - " + name + ", тип покупателя - " + type.getRus();
    }

    /**
     * Записывает информацию о покупателе для сохранения в файл
     * @return строку с информацией о покупателе
     */
    public String toStringForFiles() {
        return id + ";" + name + ";" + type;
    }

}
