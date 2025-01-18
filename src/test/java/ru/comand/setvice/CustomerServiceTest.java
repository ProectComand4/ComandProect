package ru.comand.setvice;

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

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService customerService;
    Path filePath;
    Path filePathId;

    @BeforeEach
    void setUp() throws IOException {
        filePath = Path.of("src/test/java/ru/comand/repository/Files/customer_test.txt");
        filePathId = Path.of("src/test/java/ru/comand/repository/Files/customerID_test.txt");
        Files.createFile(filePath);
        Files.createFile(filePathId);

        CustomerRepository customerRepository = new CustomerRepository(filePath.toString(), filePathId.toString());
        customerService = new CustomerService(customerRepository);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(filePath);
        Files.deleteIfExists(filePathId);
        customerService = null;
    }

    @Test
    void addCustomer_Success() {
        //given

        //when
        Customer customer = customerService.addCustomer("Ivan", CustomerType.NEW);
        //then
        assertNotNull(customer);
        assertEquals("Ivan", customer.getName());
        assertEquals(CustomerType.NEW, customer.getType());
        assertEquals(1, customerService.getAll().size());
    }

    @Test
    void getAllCustomers_findAllCustomer() {
        //given
        customerService.addCustomer("Anna", CustomerType.REGULAR);
        //when
        List<Customer> customers = customerService.getAll();
        //then
        assertEquals("Anna", customers.getFirst().getName());
    }

    @Test
    void getCustomerById_findCustomer() {
        //given
        Customer customer = customerService.addCustomer("Maks", CustomerType.VIP);
        customer.setId(1);
        //when
        Customer findCustomer = customerService.getByID(customer.getId());
        //then
        assertNotNull(findCustomer);
        assertEquals(customer.getId(), findCustomer.getId());
        assertEquals("Maks", findCustomer.getName());
    }

    @Test
    void getById_throwCustomerNotFoundException() {
        //given
        //when
        customerService.addCustomer("Ivan", CustomerType.NEW);
        //then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getByID(99));
        assertThrows(CustomerNotFoundException.class, () -> customerService.getByID(0));
        assertDoesNotThrow(() -> customerService.getByID(1));

    }

}
