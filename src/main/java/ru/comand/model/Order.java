package ru.comand.model;


import java.util.List;
import java.util.Objects;

public class Order {
    private Integer id;
    private Customer customer;
    private List<Product> products;
    private String status;

    public Order(Integer id, Customer customer, List<Product> products, String status) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(products, order.products) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, products, status);
    }

    @Override
    public String toString() {
        return "id = " + id + ", покупатель - " + customer + ", состав заказа - " + products + ", статус заказа - " + status;
    }
}
