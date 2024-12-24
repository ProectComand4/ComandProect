package ru.comand;

import ru.comand.controller.CustomerController;
import ru.comand.controller.MainController;
import ru.comand.controller.ProductController;
import ru.comand.model.Product;
import ru.comand.repository.CustomerRepository;
import ru.comand.repository.ProductRepository;
import ru.comand.service.CustomerService;
import ru.comand.service.ProductService;

public class Main {
    public static void main(String[] args) {

        Product product = new Product(1, "Яблоко", 200, "Food");
        System.out.println(product);


        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);

        CustomerRepository customerRepository = new CustomerRepository("src/main/java/ru/comand/repository/Files/customer.txt");
        CustomerService customerService = new CustomerService(customerRepository);
        CustomerController customerController = new CustomerController(customerService);

        MainController mainController = new MainController(productController, customerController);
        mainController.start();


    }
}