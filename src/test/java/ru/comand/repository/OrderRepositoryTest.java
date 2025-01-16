package ru.comand.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.comand.Enums.CategoryProduct;
import ru.comand.Enums.CustomerType;
import ru.comand.Enums.OrderStatus;
import ru.comand.model.Customer;
import ru.comand.model.Order;
import ru.comand.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    private final String filePath =
            "src/test/java/ru/comand/repository/Files/order.txt";
    private final String fileIdPath =
            "src/test/java/ru/comand/repository/Files/orderId.txt";
    private final Product testProduct =
            new Product("Test Product", 112, CategoryProduct.CLOTHING);
    private final Customer testCustomer = new Customer("Test Customer", CustomerType.NEW);

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
    void addOrder_saveFromFile() throws IOException {
        //give
        OrderRepository orderRepository = new OrderRepository(filePath, fileIdPath);
        List<Product> productList = new ArrayList<>();
        //when
        productList.add(testProduct);
        Order testOrder = new Order(testCustomer, productList, OrderStatus.NEW);
        orderRepository.save(testOrder);
        //then
        assertNotNull(testOrder.getId());
        assertEquals(testOrder.getCustomer(), testCustomer);
        assertEquals(testOrder.getStatus(), OrderStatus.NEW);


    }

    @Test
    void findAll_Order_Test() {
        //give
        OrderRepository orderRepository = new OrderRepository(filePath, fileIdPath);
        List<Product> productList = new ArrayList<>();
        //when
        productList.add(testProduct);
        Order testOrder = new Order(testCustomer, productList, OrderStatus.NEW);
        orderRepository.save(testOrder);
        //then
        assertNotNull(testOrder.getId());
        assertEquals(testOrder.getCustomer(), testCustomer);
        assertNotNull(testOrder.getId()); //Id заказа устанавливается в другом методе


    }

}