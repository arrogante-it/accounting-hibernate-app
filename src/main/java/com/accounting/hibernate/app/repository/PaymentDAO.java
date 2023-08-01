package com.accounting.hibernate.app.repository;

import com.accounting.hibernate.app.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDAO {
    Payment getById(Long id);

    List<Payment> getAll();

    void save(Payment payment);

    void delete(Payment payment);

    void update(Payment payment);

    List<Payment> findAllByContract(Long contractId);

    List<Payment> findAllByCustomer(Long customerId);

    List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount);
}
