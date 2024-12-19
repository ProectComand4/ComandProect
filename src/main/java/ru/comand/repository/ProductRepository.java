package ru.comand.repository;

import ru.comand.Exceptions.ProductNotFoundException;
import ru.comand.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final List<Product> products;
    private int countId;

    public ProductRepository() {
        this.products = new ArrayList<>();
        countId = 0;
    }


    public Product save(Product product) {
        product.setId(++countId);

        products.add(product);
        return product;
    }

    public List<Product> findAll() {
        return products;
    }

    public Product findById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }


}


