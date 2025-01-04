package ru.comand.controller;

import ru.comand.Exceptions.CustomerNotFoundException;
import ru.comand.repository.CustomerRepository;
import ru.comand.repository.OrderRepository;
import ru.comand.repository.ProductRepository;
import ru.comand.service.CustomerService;
import ru.comand.service.OrderService;
import ru.comand.service.ProductService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    Scanner scanner = new Scanner(System.in);

    /**
     * Запуск программы
     */
    public void startProgram() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);

        CustomerRepository customerRepository = new CustomerRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        CustomerController customerController = new CustomerController(customerService);

        OrderRepository orderRepository = new OrderRepository();
        OrderService orderService = new OrderService(orderRepository);
        OrderController orderController = new OrderController(orderService,customerController,productController);


        while (true) {
            System.out.println("\n1. Управление покупателями");
            System.out.println("2. Показать всех покупателей");
            System.out.println("3. Управление продуктами");
            System.out.println("4. Показать все продукты");
            System.out.println("5. Управление заказами");
            System.out.println("6. Показать все заказы");
            System.out.print("\nВыберите нужную опцию: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> customerController.start();
                    case 2 -> customerController.getAllCustomers();
                    case 3 -> productController.start();
                    case 4 -> productController.getAllProducts();
                    case 5 -> orderController.start();
                    case 6 -> orderController.getAllOrders();
                    default -> System.out.println("Введите число из предложенных");
                }
            } catch (CustomerNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Введите число");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Ошибка " + e.getMessage());
            }
        }


    }


}
