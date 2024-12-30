package ru.comand.controller;

import ru.comand.repository.ProductRepository;
import ru.comand.service.ProductService;

public class MainController {
    public void startProduct() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);

        productController.start();
    }

}
