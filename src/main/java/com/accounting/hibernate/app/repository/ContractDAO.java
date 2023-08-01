package com.accounting.hibernate.app.repository;

import com.accounting.hibernate.app.model.Contract;

import java.util.List;

public interface ContractDAO {
    Contract getById(Long id);

    List<Contract> getAll();

    void save(Contract contract);

    void update(Contract contract);

    void delete(Contract contract);
}
