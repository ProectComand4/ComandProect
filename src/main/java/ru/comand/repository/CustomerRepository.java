package ru.comand.repository;

import ru.comand.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private final List<Customer> customers;
    private int countID;

    public CustomerRepository() {
        this.customers = new ArrayList<>();
        countID = 0;
    }

    public Customer saveCustomer(Customer customer) {
        customer.setId(++countID);
        customers.add(customer);
        return customer;
    }

    public List<Customer> findAllCustomers() {
        return customers;
    }

    public Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }
}
