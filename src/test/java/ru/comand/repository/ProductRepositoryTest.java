package ru.comand.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.comand.Enums.CategoryProduct;
import ru.comand.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    private final String filePath =
            "src/test/java/ru/comand/repository/Files/product.txt";
    private final String fileIdPath =
            "src/test/java/ru/comand/repository/Files/productId.txt";
    private final Product testProduct =
            new Product( "test", 112, CategoryProduct.FOOD);
    private final Integer id = 1;

    @BeforeEach
    void setUp() {
        try {
            Files.createFile(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.createFile(Path.of(fileIdPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {
        try {
            Files.delete(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(Path.of(fileIdPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void save_Product_Test() {
        //give
        ProductRepository productRepository = new ProductRepository(filePath, fileIdPath);
        //when
        productRepository.save(testProduct);
        //then
        assertNotNull(productRepository.findById(id));
        assertEquals(testProduct, productRepository.findById(id));
        assertEquals(1, productRepository.findAll().size());

    }

    @Test
    void findAll_Product_Test() {
        //give
        ProductRepository productRepository = new ProductRepository(filePath, fileIdPath);
        //when
        productRepository.save(testProduct);
        List<Product> products = productRepository.findAll();
        //then
        assertNotNull(products);
        assertEquals(testProduct, products.get(0));

    }


}