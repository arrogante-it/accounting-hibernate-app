package com.accounting.hibernate.app.persistence.repository.impl;

import com.accounting.hibernate.app.persistence.model.Contract;
import com.accounting.hibernate.app.persistence.repository.ContractDao;
import com.accounting.hibernate.app.persistence.util.EntityManagerUtil;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ContractDaoImpl implements ContractDao {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Contract contract) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(entityManager ->
                        entityManager.persist(contract));
    }

    @Override
    public void update(Contract contract) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(entityManager ->
                        entityManager.merge(contract));
    }

    @Override
    public void delete(Contract contract) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(entityManager -> {
                    Contract contractMerged = entityManager.merge(contract);
                    entityManager.remove(contractMerged);
                });
    }

    @Override
    public Contract getById(Long id) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(entityManager ->
                        entityManager.createQuery("select c from Contract c join fetch c.customer " +
                                "where c.id = :id", Contract.class)
                                .setParameter("id", id)
                                .getSingleResult()
                );
    }


    @Override
    public List<Contract> getAll() {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(entityManager ->
                        entityManager.createQuery("select c from Contract c join fetch c.customer ", Contract.class)
                                .getResultList());
    }
}
