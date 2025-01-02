package ru.comand.controller;


import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.Customer;
import ru.comand.model.Product;
import ru.comand.service.OrderService;

import java.util.InputMismatchException;
import java.util.Scanner;


public class OrderController {
    private final OrderService orderService;
    private final CustomerController customerController;
    private final ProductController productController;
    Product product;
    Customer customer;

    public OrderController(OrderService orderService, CustomerController customerController, ProductController productController) {
        this.orderService = orderService;
        this.customerController = customerController;
        this.productController = productController;
    }


    public void start() {
        while (true) {
            System.out.print("\n 1.Создать заказ");
            System.out.print("\n 2.Список всех заказов");
            System.out.print("\n 3.Показать заказ по его ID ");
            System.out.print("\n 0.Назад ");
            System.out.print("\nВыберите нужную опцию");


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
                        case 2 -> getAllOrders();
                        case 3 -> {
                            System.out.println("Введите Id заказа ");
                            Scanner scan2 = new Scanner(System.in);
                            int id = scan2.nextInt();
                            getIndexOrder(id);

                        }
                        default -> System.out.println("Ошибка повторите");
                    }
                } catch (ProductNotFoundException e) {
                    System.out.println("Заказа с таким Id нету");

                } catch (Exception e) {
                    System.out.println();

                }

            } catch (InputMismatchException e) {
                System.out.println("Введите цифру");

            }


        }
    }

    private void addOrder() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Список покупателей");
        customerController.getAllCustomers();
        System.out.println("Введите id покупателя");
        int id1 = scan.nextInt();
        System.out.println("Список продуктов");
        productController.getAllProducts();
        System.out.println("Введите id продукта");
        int id2 = scan.nextInt();

        customer = customerController.getCustomerById(id1);
        product = productController.getIndexProduct(id2);


        String view = orderService.addOrder(customer, product).toString();
        System.out.println(view);

    }

    public void getAllOrders() {
        String view = orderService.getAll().toString();
        System.out.println(view);
    }

    /**
     * Выводит заказ с указанным ID
     * @param id Id заказа
     */
    private void getIndexOrder(int id) {
        try {
            String view = orderService.getById(id).toString();
            System.out.println(view);
        } catch (NullPointerException e) {
            throw new ProductNotFoundException("Продукта с данным ID не нашлось");
        }
    }
}
