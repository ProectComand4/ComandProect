package ru.comand.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.OrderStatus;
import ru.comand.Exceptions.OrderNotFoundException;
import ru.comand.model.Customer;
import ru.comand.model.Order;
import ru.comand.model.Product;
import ru.comand.service.CustomerService;
import ru.comand.service.OrderService;
import ru.comand.service.ProductService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class OrderController {
    Scanner scanner = new Scanner(System.in);
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private OrderStatus orderStatus;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final CustomerController customerController;
    private final ProductController productController;

    public OrderController(OrderService orderService, CustomerService customerService, ProductService productService, CustomerController customerController, ProductController productController) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
        this.customerController = customerController;
        this.productController = productController;
    }

    /**
     * Управление заказами
     */
    public void start() {
        while (true) {
            System.out.println("\n===== Управление заказами =====");
            System.out.println("1. Создать заказ");
            System.out.println("2. Показать все заказы");
            System.out.println("3. Изменить статус заказа");
            System.out.println("4. Найти заказ по ID");
            System.out.println("0. Назад");
            System.out.print("\nВыберите нужную опцию: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0 -> {
                        System.out.println("Выход в главное меню");
                        return;
                    }
                    case 1 -> addOrder();
                    case 2 -> {
                        if (orderService.getAll().isEmpty()) {
                            addOrder();
                        }
                        getAllOrder();
                    }
                    case 3 -> {
                        if (orderService.getAll().isEmpty()) {
                            addOrder();
                        }
                        getAllOrder();
                        System.out.println("Статус какого заказа нужно поменять: ");
                        int orderID = scanner.nextInt();
                        getOrderById(orderID);
                        for (OrderStatus os : OrderStatus.values()) {
                            if (os != OrderStatus.NEW) {
                                System.out.println(os.ordinal() + " " + os.getRus());
                            }
                        }
                        System.out.println("Выберите статус заказа: ");
                        int statusChoice = scanner.nextInt();
                        orderStatus = OrderStatus.changeOrderStatus(statusChoice);
                        changeStatus(orderID);
                    }
                    case 4 -> {
                        if (orderService.getAll().isEmpty()) {
                            addOrder();
                        }
                        System.out.print("Введите ID заказа: ");
                        int id = scanner.nextInt();
                        getOrderById(id);
                    }
                    default -> System.out.println("Введите число из предложенных");
                }
            } catch (OrderNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Введите число");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Ошибка " + e.getMessage());
            }
        }
    }

    /**
     * Добавляет новый заказ
     */
    private void addOrder() {
        if (customerService.getAll().isEmpty()) {
            customerController.start();
        }
        System.out.println(customerService.getAll());
        System.out.println("Выберите покупателя по ID: ");
        int customerChoice = scanner.nextInt();
        Customer customer = customerService.getByID(customerChoice);

        List<Product> products = new ArrayList<>();
        boolean hasProduct = true;
        if (productService.getAll().isEmpty()) {
            logger.warn("Список товаров пуст");
            System.out.println("Добавьте товары: ");
            productController.start();
        }
        while (hasProduct) {
            System.out.println(productService.getAll());
            System.out.println("\nДобавить товар в заказ по ID: ");
            int productChoice = scanner.nextInt();
            products.add(productService.getById(productChoice));
            System.out.println("\nДобавить еще: ");
            System.out.println("1 - да: ");
            System.out.println("0 - нет: ");
            int addProductChoice = scanner.nextInt();
            if (addProductChoice < 0 || addProductChoice > 1) {
                logger.warn("Неподходящее число");
                System.out.println("Введите число из предложенных");
            }
            hasProduct = addProductChoice == 1;
        }
        orderStatus = OrderStatus.NEW;

        String view = orderService.addOrder(customer, products, orderStatus).toString();
        System.out.print("Добавлен заказ - " + view);
    }

    /**
     * Выводит список всех заказов
     */
    private void getAllOrder() {
        String view = orderService.getAll().toString();
        System.out.println(view);
    }

    /**
     * Выводит заказ с указанным ID
     * @param id ID заказа
     */
    private void getOrderById(int id) {
        try {
            String view = orderService.getByID(id).toString();
            System.out.println(view);
        } catch (NullPointerException e) {
            throw new OrderNotFoundException(id);
        }
    }

    /**
     * Меняет статус заказа
     * @param orderID ID заказа
     */
    private void changeStatus(int orderID) {
        Order order = orderService.getByID(orderID);
        if (order != null) {
            order.setStatus(orderStatus);
            String view = orderService.addOrder(order.getCustomer(), order.getProduct(), orderStatus).toString();
            System.out.println("Статус успешно изменён " + view);
        } else {
            logger.warn("Список заказов пуст");
            System.out.println("Список заказов пуст. Создайте новый заказ");
            addOrder();
        }
    }
}
