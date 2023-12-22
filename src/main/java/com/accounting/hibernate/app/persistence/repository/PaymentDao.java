package com.accounting.hibernate.app.persistence.repository;

import com.accounting.hibernate.app.persistence.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDao {
    void save(Payment payment);

    void update(Payment payment);

    void delete(Payment payment);

    Payment getById(Long id);

    List<Payment> getAll();

    List<Payment> findAllByContract(Long contractId);

    List<Payment> findAllByCustomer(Long customerId);

    List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount);
}
