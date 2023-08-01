package com.accounting.hibernate.app.repository;

import com.accounting.hibernate.app.model.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer getById(Long id);

    List<Customer> getAll();

    void save(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);
}
