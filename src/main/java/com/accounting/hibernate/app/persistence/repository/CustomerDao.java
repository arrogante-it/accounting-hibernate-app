package com.accounting.hibernate.app.persistence.repository;

import com.accounting.hibernate.app.persistence.model.Customer;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface CustomerDao {
    void save(Customer customer, EntityManager entityManager);

    void update(Customer customer, EntityManager entityManager);

    void delete(Customer customer, EntityManager entityManager);

    Customer getById(Long id, EntityManager entityManager);

    List<Customer> getAll(EntityManager entityManager);
}
