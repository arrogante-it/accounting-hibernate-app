package com.accounting.hibernate.app.persistence.repository;

import com.accounting.hibernate.app.persistence.model.Customer;

import java.util.List;

public interface CustomerDao {
    void save(Customer customer);

    void update(Customer customer);

    void delete(Customer customer);

    Customer getById(Long id);

    List<Customer> getAll();
}
