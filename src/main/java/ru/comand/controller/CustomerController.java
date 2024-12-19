package ru.comand.controller;

import ru.comand.Exceptions.CustomerNotFoundException;
import ru.comand.service.CustomerService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerController {
    Scanner scanner = new Scanner(System.in);
    private final CustomerService customerService;
    String customerName;
    String customerType;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void start() {
        while (true) {
            System.out.println("\n1. Добавить покупателя");
            System.out.println("2. Показать всех покупателей");
            System.out.println("3. Найти покупателя по ID");
            System.out.println("0. Назад");
            System.out.print("\nВыберите нужную опцию: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0 -> {
                        System.out.println("Выход в главное меню");
                        return;
                    }
                    case 1 -> addCustomer();
                    case 2 -> getAllCustomers();
                    case 3 -> {
                        System.out.print("\nВведите ID покупателя: ");
                        int id = scanner.nextInt();
                        getCustomerById(id);
                    }
                    default -> System.out.println("Введите число из предложенных");
                }
            } catch (CustomerNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Введите число");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ошибка " + e.getMessage());
            }
        }
    }

    private void addCustomer() {
        while ((customerName = scanner.nextLine()).isEmpty()) {
            System.out.print("\nВведите имя покупателя: ");

        }
        while (customerType == null) {
            System.out.println("Выберите статус покупателя: " +
                    "\n1. NEW" +
                    "\n2. REGULAR" +
                    "\n3. VIP");
            try {
                int choice = scanner.nextInt();
                // scanner.nextLine();
                switch (choice) {
                    case 1 -> customerType = "NEW";
                    case 2 -> customerType = "REGULAR";
                    case 3 -> customerType = "VIP";
                    default -> System.out.println("Введите число из предложенных");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Введите число");
                scanner.nextLine();
            }
        }
        String view = customerService.addCustomer(customerName, customerType).toString();
        System.out.println("Добавлен покупатель - " + view);
        customerType = null;
    }

    private void getAllCustomers() {
        String view = customerService.getAll().toString();
        System.out.println(view);
    }

    private void getCustomerById(int id) {
        try {
            String view = customerService.getByID(id).toString();
            System.out.println(view);
        } catch (NullPointerException e) {
            throw new CustomerNotFoundException("Покупателя с таким ID нет");
        }
    }
}
