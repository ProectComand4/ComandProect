package ru.comand.model;


import ru.comand.Enums.OrderStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Order {
    private Integer id;
    private Customer customer;
    private List<Product> product;
    private OrderStatus status;

    public Order(Customer customer, List<Product> product, OrderStatus status) {
        this.id = null;
        this.customer = customer;
        this.product = product;
        this.status = status;
    }

    public Order(String orderFromFile) {
        String[] parts = orderFromFile.split("-");
        this.id = Integer.parseInt(parts[0]);

        this.customer = new Customer(parts[1]);

        String[] productsFromFile = parts[2].replaceAll("[\\[\\]\\s]", "").split(",");
        this.product = Arrays.stream(productsFromFile)
                .map(Product::new)
                .toList();

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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
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
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(product, order.product) && status == order.status;
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
        List<String> products = new ArrayList<>();
        for (Product product1 : product) {
            products.add(product1.toStringForFiles());
        }
        return id + "-" + customer.toStringForFiles() + "-" + products + "-" + status;
    }

}
