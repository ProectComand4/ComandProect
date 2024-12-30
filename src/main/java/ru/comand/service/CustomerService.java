package ru.comand.service;

import ru.comand.Enums.CustomerType;
import ru.comand.model.Customer;
import ru.comand.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

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
        return customerRepository.findAll();
    }

    /**
     * Получает покупателя с указанным ID из репозитория
     * @param id ID покупателя
     * @return покупатель с указанным ID
     */
    public Customer getByID(int id) {
        return customerRepository.findById(id);
    }
}
