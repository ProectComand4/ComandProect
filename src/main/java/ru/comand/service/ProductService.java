package ru.comand.service;
import ru.comand.model.Product;
import ru.comand.Enums.CategoryProduct;
import ru.comand.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository bookRepository) {
        this.productRepository = bookRepository;
    }

    /**
     * Добавляет информацию о продукте в репозиторий
     * @param name имя продукта
     * @param price цену продукта
     * @param category тип продукта
     * @return сохранённого продукта
     */
    public String addProduct(String name, Integer price, CategoryProduct category) {
        Product newProduct = new Product(null, name, price, category);
        productRepository.save(newProduct);
        return newProduct.toString();
    }

    /**
     * Получает список всех продуктов из репозитория
     * @return список всех продуктов
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Получает продукт с указанным ID из репозитория
     * @param id ID продукта
     * @return продукт с указанным ID
     */
    public Product getById(int id) {
        return productRepository.findById(id);


    }
}
