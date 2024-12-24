package ru.comand.controller;


import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    Scanner scanner = new Scanner(System.in);
    private final ProductController productController;
    private final CustomerController customerController;
    /*oder controller*/

    public MainController(ProductController productController, CustomerController customerController /*oder controller*/) {
        this.productController = productController;
        this.customerController = customerController;
        /*oder controller*/
    }

    /**
     * Управление главным меню
     */
    public void start() {
        while (true) {
            System.out.println("\nГлавное меню: ");
            System.out.println("1. Управление товарами");
            System.out.println("2. Управление покупателями");
            System.out.println("3. Управление заказами");
            System.out.println("0. Выйти");
            System.out.print("\nВыберите нужную опцию: ");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0 -> {
                        System.out.println("Вы вышли из программы");
                        return;
                    }
                    case 1 -> productController.start();
                    case 2 -> customerController.start();
                    case 3 -> System.out.print("Управление заказами");
                    default -> System.out.println("Введите число из предложенных");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка. Введите число");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Ошибка " + e.getMessage());
            }
        }
    }
}
