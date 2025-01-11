package ru.comand.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.OrderStatus;
import ru.comand.model.Customer;
import ru.comand.model.Order;
import ru.comand.model.Product;
import ru.comand.repository.OrderRepository;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Добавляет информацию о заказе в репозиторий
     * @param customer покупатель
     * @param products список покупок
     * @param status статус заказа
     * @return сохранённый заказ
     */
    public Order addOrder(Customer customer, List<Product> products, OrderStatus status) {
        Order newOrder = new Order(customer, products, status);
        return orderRepository.save(newOrder);
    }

    /**
     * Получает список всех заказов из репозитория
     * @return список всех заказов
     */
    public List<Order> getAll() {
        try {
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                logger.warn("Список заказов пуст");
                System.out.println("Добавьте новый заказ: ");
            }
            return orders;
        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Получает заказ с указанным ID из репозитория
     * @param id ID заказа
     * @return заказ с указанным ID
     */
    public Order getByID(int id) {
        return orderRepository.findById(id);
    }
}