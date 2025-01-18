package ru.comand.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.comand.Enums.CustomerType;
import ru.comand.Exceptions.CustomerNotFoundException;
import ru.comand.model.Customer;
import ru.comand.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Добавляет информацию о покупателе в репозиторий
     * @param name имя покупателя
     * @param type тип покупателя
     * @return сохранённого покупателя
     */
    public Customer addCustomer(String name, CustomerType type) {
        Customer newCustomer = new Customer(name, type);
        return customerRepository.save(newCustomer);
    }

    /**
     * Получает список всех покупателей из репозитория
     * @return список всех покупателей
     */
    public List<Customer> getAll() {
        try {
            List<Customer> customers = customerRepository.findAll();
            if (customers.isEmpty()) {
                logger.warn("Список покупателей пуст");
                System.out.println("Добавьте нового покупателя: ");
            }
            return customers;
        } catch (Exception e) {
            logger.error("Ошибка: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Получает покупателя с указанным ID из репозитория
     * @param id ID покупателя
     * @return покупатель с указанным ID
     */
    public Customer getByID(int id) {
        if (customerRepository.findById(id) == null) {
            throw new CustomerNotFoundException(id);
        }
        return customerRepository.findById(id);
    }
}