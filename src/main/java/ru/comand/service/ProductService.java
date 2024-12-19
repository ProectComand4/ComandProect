package ru.comand.service;

import ru.comand.model.Product;
import ru.comand.repository.ProductRepository;

import java.util.List;

public class ProductService {


    private final ProductRepository productRepository;


    public ProductService(ProductRepository bookRepository) {
        this.productRepository = bookRepository; // внедрение зависимостей // dependency injection
    }

    public Product addProduct(String name, Integer price, String category) {
        Product newProduct = new Product(null, name, price, category);

        return productRepository.save(newProduct);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getIndexProduct(int index) {
        return productRepository.findById(index);


    }
}
