package ru.comand.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.OrderStatus;
import ru.comand.Exceptions.CustomerNotFoundException;
import ru.comand.Exceptions.OrderNotFoundException;
import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.Customer;
import ru.comand.model.Order;
import ru.comand.model.Product;
import ru.comand.service.OrderService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class OrderController {
    Scanner scan = new Scanner(System.in);
    private final OrderService orderService;
    private final CustomerController customerController;
    private final ProductController productController;
    final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private Customer customer;

    public OrderController(OrderService orderService, CustomerController customerController, ProductController productController) {
        this.orderService = orderService;
        this.customerController = customerController;
        this.productController = productController;
    }

    public void start() {
        while (true) {
            System.out.print("\n1.Создать заказ");
            System.out.println("\n2.Список всех заказов");
            System.out.println("3.Показать заказ по его ID ");
            System.out.println("4.Изменить статус заказ ");
            System.out.println("0.Назад ");
            System.out.print("\nВыберите нужную опцию: ");


            try {
                int choice = scan.nextInt();
                switch (choice) {
                    case 0 -> {
                        System.out.println("Выход в главное меню");
                        return;
                    }
                    case 1 -> add();
                    case 2 -> getAll();
                    case 3 -> {
                        getAll();
                        System.out.println("Введите Id заказа ");
                        int id = scan.nextInt();
                        System.out.println(getById(id).toString());
                    }
                    case 4 -> {
                        getAll();
                        System.out.println("Выберите заказ по ID: ");
                        int id = scan.nextInt();
                        if (getById(id) == null) {
                            throw new OrderNotFoundException(id);
                        }

                        for (OrderStatus os : OrderStatus.values()) {
                            System.out.println((os.ordinal() + 1) + " - " + os.getRus());
                        }
                        System.out.println("Выберите статус заказа: ");

                        int statusChoice = scan.nextInt();
                        OrderStatus oS = null;
                        switch (statusChoice) {
                            case 1 -> oS = OrderStatus.NEW;
                            case 2 -> oS = OrderStatus.PROCESSING;
                            case 3 -> oS = OrderStatus.COMPLETED;
                            case 4 -> oS = OrderStatus.CANCELLED;
                            default -> logger.warn("Введите цифру из представленных");
                        }
                        System.out.println("Статус заказа успешно изменён - ");
                        changeOrderStatus(getById(id), oS);
                    }
                    default -> System.out.println("Ошибка повторите");
                }
            } catch (OrderNotFoundException e) {
                logger.warn(e.getMessage());
            } catch (InputMismatchException e) {
                logger.debug("Введите цифру");
                scan.next();
            } catch (Exception e) {
                logger.error("{}{}", e.getMessage(), getClass().getName());
            }

        }
    }

    private void add() {
        try {
            if (customerController.getAll().isEmpty()) {
                customerController.start();
            }
            System.out.println(customerController.getAll());
            while (customer == null) {
                System.out.println("Введите id покупателя: ");
                try {
                    int id1 = scan.nextInt();
                    customer = customerController.getById(id1);
                } catch (CustomerNotFoundException e) {
                    logger.warn("Значение ID вне диапазона");
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    logger.warn("Неверный символ");
                    System.out.println("Ошибка. Введите число");
                    scan.next();
                } catch (Exception e) {
                    logger.error("Ошибка {}", e.getMessage());
                }
            }
            if (productController.getAll().isEmpty()) {
                logger.warn("Список товаров пуст");
                System.out.println("Добавьте товары: ");
                productController.start();
            }

            List<Product> products = new ArrayList<>();
            boolean hasProduct = true;
            while (hasProduct) {
                try {
                    System.out.println("\nСписок продуктов");
                    System.out.println(productController.getAll());
                    System.out.println("\nДобавить товар в заказ по ID: ");
                    int productChoice = scan.nextInt();
                    if (productController.getById(productChoice) == null) {
                        throw new ProductNotFoundException("Товар с указанным ID не найден");
                    }
                    products.add(productController.getById(productChoice));

                    System.out.println("Добавить еще: ");
                    System.out.println("1 - да: ");
                    System.out.println("0 - нет: ");
                    int addProductChoice = scan.nextInt();
                    if (addProductChoice < 0 || addProductChoice > 1) {
                        logger.warn("Неподходящее число");
                        System.out.println("Введите число из предложенных");
                        addProductChoice = 1;
                    }
                    hasProduct = addProductChoice == 1;
                } catch (InputMismatchException e) {
                    logger.warn("Неверный символ");
                    System.out.println("Ошибка. Введите число");
                    scan.next();
                } catch (Exception e) {
                    logger.error("Ошибка {}", e.getMessage());
                }
            }

            OrderStatus orderStatus = OrderStatus.NEW;

            try {
                String view = orderService.addOrder(customer, products, orderStatus).toString();
                System.out.println(view);
            } catch (Exception e) {
                logger.error("Ошибка при создании заказа: {}", e.getMessage());
                System.out.println("Произошла ошибка при добавлении заказа: " + e.getMessage());
            }

        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            customer = null;
        }
    }

    public void getAll() {
        System.out.println(orderService.getAll().toString());
    }

    /**
     * Выводит заказ с указанным ID
     * @param id Id заказа
     */
    public Order getById(int id) {
        if (orderService.getById(id) == null) {
            throw new OrderNotFoundException(id);
        }
        return orderService.getById(id);
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        List<Order> list = orderService.getAll();
        Order orders = list.stream()
                .filter(order1 -> order1.equals(order))
                .peek(order1 -> order1.setStatus(status))
                .findFirst().orElse(null);
        orderService.saveFromFile(list);
        System.out.println(orders);
    }
}
