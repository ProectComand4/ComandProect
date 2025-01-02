package ru.comand.service;

import ru.comand.Enums.OrderStatus;
import ru.comand.model.Customer;
import ru.comand.model.Order;
import ru.comand.model.Product;
import ru.comand.repository.OrderRepository;

import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Добавляет информацию о заказе в репозиторий
     * @param customer Покупатель
     * @param product Продукт
     * @return заказ
     */
    public Order addOrder(Customer customer, Product product) {
        Order newOrder = new Order(null, customer, product, OrderStatus.NEW);
        return orderRepository.save(newOrder);
    }

    /**
     * Получает список всех заказа из репозитория
     * @return список всех заказа
     */
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    /**
     * Получает заказа с указанным ID из репозитория
     * @param id ID заказа
     * @return заказа с указанным ID
     */
    public Order getById(int id) {
        return orderRepository.findById(id);
    }

}
