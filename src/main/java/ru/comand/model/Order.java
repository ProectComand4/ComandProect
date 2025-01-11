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
        try {
            String[] parts = orderFromFile.split(";");
            this.id = Integer.parseInt(parts[0]);
            this.customer = Customer.toCustomer(Integer.parseInt(parts[1]));
            String[] productId = parts[2].replaceAll("[\\[\\]\\s]", "").split(",");
            this.product = Arrays.stream(productId)
                    .map(id -> Product.toProduct(Integer.parseInt(id)))
                    .toList();
            this.status = OrderStatus.toOrderStatus(parts[3]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при чтении файла " + e.getMessage());
        }
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
        return "\nid - " + id + ", покупатель - " + customer +
                ", список покупок - " + product +
                ", статус заказа - " + status.getRus() + "\n";
    }

    /**
     * Записывает информацию о заказе для сохранения в файл
     * @return строку с информацией о заказе
     */
    public String toStringForFiles() {
        List<Integer> productsId = new ArrayList<>();
        for (Product product1 : product) {
            productsId.add(product1.getId());
        }
        return id + ";" + customer.getId() + ";" + productsId + ";" + status;
    }
}
