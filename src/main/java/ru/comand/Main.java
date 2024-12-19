package ru.comand;

import ru.comand.controller.CustomerController;
import ru.comand.model.Product;
import ru.comand.repository.CustomerRepository;
import ru.comand.service.CustomerService;

public class Main {
    public static void main(String[] args) {

        Product product = new Product(1, "Яблоко", 200, "Food");
        System.out.println(product);

        CustomerRepository customerRepository = new CustomerRepository();
        CustomerService customerService = new CustomerService(customerRepository);
        CustomerController customerController = new CustomerController(customerService);

        customerController.start();
    }
}