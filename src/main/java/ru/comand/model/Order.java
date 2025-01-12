package ru.comand.model;


import ru.comand.Enums.CustomerType;
import ru.comand.Enums.OrderStatus;

import java.util.List;
import java.util.Objects;

public class Order {
    private Integer id;
    private Customer customer;
    private Product product;
    private OrderStatus status;

    public Order(Integer id, Customer customer, Product product, OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.status = status;
    }

    public Order(String customerFromFile) {
        String[] parts = customerFromFile.split("-");
        this.id = Integer.parseInt(parts[0]);

        this.customer = new Customer(parts[1]);

        this.product = new Product(parts[2]);

        this.status = OrderStatus.toOrderStatus(parts[3]);
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(product, order.product) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, product, status);
    }

    @Override
    public String toString() {
        return
                id + "-" + customer + "-" + product + "-" + status.getRus() + "\n";
    }

    public String toStringForFiles() {
        return id + "-" + customer.toStringForFiles() + "-" + product.toStringForFiles() + "-" + status;
    }

}
