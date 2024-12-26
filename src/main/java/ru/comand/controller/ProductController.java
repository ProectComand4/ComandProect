package ru.comand.controller;

import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.enums.CategoryProduct;
import ru.comand.service.ProductService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    String productName;
    Integer productPrice;
    CategoryProduct productCategory;

    public ProductController(ProductService service) {
        this.productService = service;
    }
    /**
     * Управление продуктами
     */
    public void start() {
        while (true) {
            System.out.print("\n 1.Создать товар");
            System.out.print("\n 2.Список всех товаров");
            System.out.print("\n 3.Показать продукт по его ID ");

            System.out.print("\nВыберите нужную опцию");


            try {
                Scanner scan = new Scanner(System.in);
                int choice = scan.nextInt();
                try {
                    switch (choice) {
                        case 1 -> addProduct();
                        case 2 -> getAllProducts();
                        case 3 -> {
                            System.out.println("Введите Id продукта ");
                            Scanner scan2 = new Scanner(System.in);
                            int id = scan2.nextInt();
                            getIndexProduct(id);
                        }
                        default -> System.out.println("Ошибка повторите");
                    }
                } catch (ProductNotFoundException e) {
                    System.out.println("Товара с таким Id нету");

                } catch (Exception e) {
                    System.out.println();

                }

            } catch (InputMismatchException e) {
                System.out.println("Введите цифру");

            }


        }

    }
    /**
     * Добавляет новый продукт
     */

    public void addProduct() {
        while (productName == null) {
            System.out.println("Здравствуйте введите название товара ");
            Scanner scan = new Scanner(System.in);
            productName = scan.nextLine();
        }
        while (productPrice == null) {

            System.out.println("Введите цену товара ");

            try {
                Scanner scan = new Scanner(System.in);
                productPrice = scan.nextInt();
            } catch (InputMismatchException _) {

            }
        }


        System.out.println("Выберите категорию товара");

        while (productCategory == null) {

            System.out.println("1.FOOD  " +
                    "\n 2.ELECTRONICS " +
                    "\n 3.CLOTHING");
            try {
                Scanner scan = new Scanner(System.in);
                int choice = scan.nextInt();
                try {
                    switch (choice) {
                        case 1 -> productCategory = CategoryProduct.FOOD;
                        case 2 -> productCategory = CategoryProduct.ELECTRONICS;
                        case 3 -> productCategory = CategoryProduct.CLOTHING;
                        default -> System.out.println("Ошибка повторите");
                    }
                } catch (Exception e) {
                    System.out.println("Введите цифру из представленных");
                }
            } catch (InputMismatchException e) {
                System.out.println("Введите цифру из представленных");
            }
        }
        String view = productService.addProduct(productName, productPrice, productCategory);
        System.out.println(view);
        productName = null;
        productPrice = null;
        productCategory = null;


    }
    /**
     * Выводит список всех продуктов
     */
    private void getAllProducts() {
        String view = productService.getAll().toString();
        System.out.println(view);
    }
    /**
     * Выводит продукт с указанным ID
     * @param id ID продукта
     */
    private void getIndexProduct(int id) {
        try {
            String view = productService.getById(id).toString();
            System.out.println(view);
        } catch (NullPointerException e) {
            throw new ProductNotFoundException("Продукта с данным ID не нашлось");
        }
    }

}
