package ru.comand.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.OrderStatus;
import ru.comand.Exceptions.ProductNotFoundException;
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
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
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
                            System.out.println("Выберите заказ по ID: ");
                            Scanner scan2 = new Scanner(System.in);
                            int id = scan2.nextInt();

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
                            changeOrderStatus(getIndexOrder(id), oS);
                        }
                        default -> System.out.println("Ошибка повторите");
                    }
                } catch (ProductNotFoundException e) {
                    logger.warn("Товара с таким Id нет");

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
            System.out.println(customerService.getAll());
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }


        System.out.println("Введите id покупателя");
        int id1 = scan.nextInt();
        Customer customer = customerService.getByID(id1);

        System.out.println("Список продуктов");
        System.out.println(productService.getAll());

        List<Product> products = new ArrayList<>();
        boolean hasProduct = true;
        while (hasProduct) {
            System.out.println("\nДобавить товар в заказ по ID: ");
            int productChoice = scan.nextInt();
            products.add(productService.getById(productChoice));
            System.out.println("Добавить еще: ");
            System.out.println("1 - да: ");
            System.out.println("0 - нет: ");
            int addProductChoice = scan.nextInt();
            if (addProductChoice < 0 || addProductChoice > 1) {
                logger.warn("Неподходящее число");
                System.out.println("Введите число из предложенных");
            }
            hasProduct = addProductChoice == 1;
        }
        OrderStatus orderStatus = OrderStatus.NEW;

        String view = orderService.addOrder(customer, products, orderStatus).toString();
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
