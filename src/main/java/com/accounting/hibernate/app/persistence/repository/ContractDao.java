package com.accounting.hibernate.app.persistence.repository;

import com.accounting.hibernate.app.persistence.model.Contract;

import java.util.List;

public interface ContractDao {
    void save(Contract contract);

    void update(Contract contract);

    void delete(Contract contract);

    Contract getById(Long id);

    List<Contract> getAll();
}
