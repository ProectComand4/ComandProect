package ru.comand.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.CategoryProduct;
import ru.comand.Enums.OrderStatus;
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
    private final OrderService orderService;
    private final CustomerController customerController;
    private final ProductController productController;
    final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    Product product;
    Customer customer;

    public OrderController(OrderService orderService,
                           CustomerController customerController,
                           ProductController productController) {
        this.orderService = orderService;
        this.customerController = customerController;
        this.productController = productController;
    }


    public void start() {
        while (true) {
            System.out.print("\n 1.Создать заказ");
            System.out.print("\n 2.Список всех заказов");
            System.out.print("\n 3.Показать заказ по его ID ");
            System.out.print("\n 4.Изменить статус заказ ");
            System.out.print("\n 0.Назад ");
            System.out.print("\n Выберите нужную опцию");


            try {
                Scanner scan = new Scanner(System.in);
                int choice = scan.nextInt();
                try {
                    switch (choice) {
                        case 0 -> {
                            System.out.println("Выход в главное меню");
                            return;
                        }
                        case 1 -> addOrder();
                        case 2 -> System.out.println(getAllOrders());
                        case 3 -> {
                            System.out.println(getAllOrders());
                            System.out.println("Введите Id заказа ");
                            Scanner scan2 = new Scanner(System.in);
                            int id = scan2.nextInt();
                            System.out.println(getIndexOrder(id).toString());

                        }
                        case 4 -> {
                            System.out.println(getAllOrders());
                            System.out.println("Введите Id заказа ");
                            Scanner scan2 = new Scanner(System.in);
                            int id = scan2.nextInt();
                            System.out.println("1 - " + OrderStatus.NEW.getRus());
                            System.out.println("2 - " + OrderStatus.PROCESSING.getRus());
                            System.out.println("3 - " + OrderStatus.COMPLETED.getRus());
                            System.out.println("4 - " + OrderStatus.CANCELLED.getRus());
                            int statusChoice = scan.nextInt();

                            OrderStatus oS = null;
                            switch (statusChoice) {
                                case 1 -> oS = OrderStatus.NEW;
                                case 2 -> oS = OrderStatus.PROCESSING;
                                case 3 -> oS = OrderStatus.COMPLETED;
                                case 4 -> oS = OrderStatus.CANCELLED;
                                default -> logger.warn("Введите цифру из представленных");
                            }


                            orderStatusNew(getIndexOrder(id), oS);
                        }
                        default -> System.out.println("Ошибка повторите");
                    }
                } catch (ProductNotFoundException e) {
                    logger.warn("Товара с таким Id нету");

                } catch (Exception e) {
                    logger.error("{}{}", e.getMessage(), getClass().getName());

                }

            } catch (InputMismatchException e) {
                logger.debug("Введите цифру");

            }


        }
    }

    private void addOrder() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Список покупателей");
        try {
            customerController.getAllCustomers();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }


        System.out.println("Введите id покупателя");
        int id1 = scan.nextInt();

        System.out.println("Список продуктов");
        System.out.println(productController.getAllProducts());

        System.out.println("Введите id продукта");
        int id2 = scan.nextInt();

        Customer customer = customerController.getCustomerById(id1);
        Product product = productController.getIndexProduct(id2);


        String view = orderService.addOrder(customer, product).toStringForFiles();
        System.out.println(view);

    }

    public String getAllOrders() {
        return orderService.getAll().toString();

    }

    /**
     * Выводит заказ с указанным ID
     * @param id Id заказа
     */
    public Order getIndexOrder(int id) {
        try {
            orderService.getById(id);
            return orderService.getById(id);
        } catch (NullPointerException e) {
            throw new ProductNotFoundException("Продукта с данным ID не нашлось");
        }
    }

    public void orderStatusNew(Order order, OrderStatus status) {

        List<Order> list = orderService.getAll();
        Order orders = list.stream()
                .filter(order1 -> order1.equals(order))
                .peek(order1 -> order1.setStatus(status))
                .findFirst().orElse(null);

        orderService.saveFromFile(list);


    }
}
