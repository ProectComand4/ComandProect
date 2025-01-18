package ru.comand.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.comand.Enums.CustomerType;
import ru.comand.Exceptions.CustomerNotFoundException;
import ru.comand.model.Customer;
import ru.comand.repository.CustomerRepository;
import ru.comand.service.CustomerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerControllerTest {
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private CustomerController customerController;
    Path filePath;
    Path filePathId;

    @BeforeEach
    void setUp() {
        filePath = Path.of("src/test/java/ru/comand/repository/Files/customer_test.txt");
        filePathId = Path.of("src/test/java/ru/comand/repository/Files/customerId_test.txt");
        customerRepository = new CustomerRepository(filePath.toString(), filePathId.toString());
        customerService = new CustomerService(customerRepository);
        customerController = new CustomerController(customerService);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.delete(filePath);
        Files.delete(filePathId);
        customerRepository = null;
        customerService = null;
        customerController = null;
    }

    @Test
    void addCustomer_Success() {
        //given
        customerController.scanner = new Scanner("Ivan\n1");
        //when
        customerController.add();
        //then;
        List<Customer> customers = customerService.getAll();
        assertEquals(1, customers.size());
        assertEquals("Ivan", customers.getFirst().getName());
        assertEquals(CustomerType.NEW, customers.getFirst().getType());
    }

    @Test
    void getAll() {
        //given
        customerService.addCustomer("Anna", CustomerType.REGULAR);
        customerService.addCustomer("Elena", CustomerType.VIP);
        //when
        List<Customer> customersFromController = customerController.getAll();
        //then
        List<Customer> customers = customerService.getAll();
        assertNotNull(customersFromController);
        assertEquals(customersFromController, customers);
        assertEquals(2, customers.size());
        assertEquals("Anna", customers.get(0).getName());
        assertEquals(CustomerType.VIP, customers.get(1).getType());
    }

    @Test
    void getByIdValidId() {
        //given
        Customer addedCustomer = customerService.addCustomer("Maks", CustomerType.VIP);
        //addedCustomer.setId(1);
        //when
        Customer findCustomer = customerService.getByID(addedCustomer.getId());
        //then
        assertNotNull(findCustomer);
        assertEquals(addedCustomer.getId(), findCustomer.getId());
        assertEquals(addedCustomer.getName(), findCustomer.getName());
        assertEquals(addedCustomer.getType(), findCustomer.getType());
    }

    @Test
    void getById_throwCustomerNotFoundException() {
        //given
        customerController.scanner = new Scanner("Olga\n1");
        //when
        customerController.add();
        //then
        assertThrows(CustomerNotFoundException.class, () -> customerController.getById(99));
        assertThrows(CustomerNotFoundException.class, () -> customerController.getById(0));
        assertDoesNotThrow(() -> customerController.getById(1));
    }

}
