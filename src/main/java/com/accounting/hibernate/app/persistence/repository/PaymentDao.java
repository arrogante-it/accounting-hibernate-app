package com.accounting.hibernate.app.persistence.repository;

import com.accounting.hibernate.app.persistence.model.Payment;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDao {
    void save(Payment payment, EntityManager entityManager);

    void update(Payment payment, EntityManager entityManager);

    void delete(Payment payment, EntityManager entityManager);

    Payment getById(Long id, EntityManager entityManager);

    List<Payment> getAll(EntityManager entityManager);

    List<Payment> findAllByContract(Long contractId, EntityManager entityManager);

    List<Payment> findAllByCustomer(Long customerId, EntityManager entityManager);

    List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount, EntityManager entityManager);
}
