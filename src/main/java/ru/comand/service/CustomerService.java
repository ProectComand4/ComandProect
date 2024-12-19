package ru.comand.service;

import ru.comand.model.Customer;
import ru.comand.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(String name, String type) {
        Customer newCustomer = new Customer(null, name, type);
        return customerRepository.saveCustomer(newCustomer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAllCustomers();
    }
    public Customer getByID(int id) {
        return customerRepository.findCustomerById(id);
    }
}
