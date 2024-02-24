package com.accounting.hibernate.app.persistence.repository;

import com.accounting.hibernate.app.persistence.model.Contract;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface ContractDao {
    void save(Contract contract, EntityManager entityManager);

    void update(Contract contract, EntityManager entityManager);

    void delete(Contract contract, EntityManager entityManager);

    Contract getById(Long id, EntityManager entityManager);

    List<Contract> getAll(EntityManager entityManager);
}
