package ru.comand.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.CustomerType;
import ru.comand.Exceptions.CustomerNotFoundException;
import ru.comand.model.Customer;
import ru.comand.service.CustomerService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerController {
    Scanner scanner = new Scanner(System.in);
    private final CustomerService customerService;
    String customerName;
    CustomerType customerType;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Управление покупателями
     */
    public void start() {
        logger.trace("Управление покупателями запущено");
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
                    case 2 -> System.out.println(getAllCustomers());
                    case 3 -> {
                        System.out.print("Введите ID покупателя: ");
                        int id = scanner.nextInt();
                        System.out.println(getCustomerById(id));
                    }
                    default -> {
                        logger.warn("Неподходящее число");
                        System.out.println("Введите число из предложенных");
                    }
                }
            } catch (CustomerNotFoundException e) {
                logger.warn("Значение ID вне диапазона - {}", e.getMessage());
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                logger.warn("Неверный символ");
                System.out.println("Ошибка. Введите число");
                scanner.next();
            } catch (Exception e) {
                logger.error("Ошибка {}", e.getMessage());
            }
        }
    }

    /**
     * Добавляет нового покупателя
     */
    private void addCustomer() {
        logger.trace("Запущен метод addCustomer()");
        while ((customerName = scanner.nextLine()).trim().isEmpty()) {
            System.out.print("Введите имя покупателя: ");
        }
        logger.debug("Заполнено поле имя покупателя - {}", customerName);
        while (customerType == null) {
            System.out.println("Выберите статус покупателя: " +
                    "\n1. Новый покупатель" +
                    "\n2. Постоянный покупатель" +
                    "\n3. VIP покупатель");
            try {
                int choice = scanner.nextInt();
                customerType = CustomerType.selectCustomerType(choice);
                logger.debug("Оределён тип покупателя - {}", customerType);
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Введите число");
                scanner.next();
            }
        }
        String view = customerService.addCustomer(customerName, customerType).toString();
        logger.info("Добавлен покупатель - {}", view);
        System.out.print("Добавлен покупатель - " + view);
        customerType = null;
    }

    /**
     * Выводит список всех покупателей
     */
    public String getAllCustomers() {
        return customerService.getAll().toString();
    }

    /**
     * Выводит покупателя с указанным ID
     * @param id ID покупателя
     */
    public Customer getCustomerById(int id) {
        try {
            return customerService.getByID(id);

        } catch (NullPointerException e) {
            logger.warn("Не найден указанный ID - {}", id);
            throw new CustomerNotFoundException("Покупателя с таким ID нет");
        }
    }
}
