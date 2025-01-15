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
    private final Path filePath =
            Path.of("src/test/java/ru/comand/repository/Files/order.txt");
    private final Product testProduct =
            new Product(1, "test", 112, CategoryProduct.FOOD);
    private final Integer id = 1;

    @BeforeEach
    void setUp() {
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void save() {
        try {
            Files.write(filePath, testProduct.toStringForFiles().getBytes(), StandardOpenOption.APPEND);
            Product result = Files.readAllLines(filePath).stream().map(Product::new).findFirst().get();
            assertArrayEquals(result.toString().getBytes(), testProduct.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void findById() {

        try {
            Files.write(filePath, testProduct.toStringForFiles().getBytes(), StandardOpenOption.APPEND);
            Product result = Files.readAllLines(filePath).stream().map(Product::new).findFirst().get();
            assertEquals(id, result.getId());

            //  assertArrayEquals(result.toString().getBytes(), testProduct.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}