package com.accounting.hibernate.app.persistence.repository.impl;

import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_CONTRACTS_BY_ID_HQL;
import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_CONTRACTS_HQL;
import com.accounting.hibernate.app.persistence.model.Contract;
import com.accounting.hibernate.app.persistence.repository.ContractDao;
import com.accounting.hibernate.app.persistence.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ContractDaoImpl implements ContractDao {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Contract contract, EntityManager entityManager) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(em ->
                        entityManager.persist(contract));
    }

    @Override
    public void update(Contract contract, EntityManager entityManager) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(em ->
                        entityManager.merge(contract));
    }

    @Override
    public void delete(Contract contract, EntityManager entityManager) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(em -> {
                    Contract contractMerged = entityManager.merge(contract);
                    entityManager.remove(contractMerged);
                });
    }

    @Override
    public Contract getById(Long id, EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_CONTRACTS_BY_ID_HQL, Contract.class)
                                .setParameter("id", id)
                                .getSingleResult()
                );
    }


    @Override
    public List<Contract> getAll(EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_CONTRACTS_HQL, Contract.class)
                                .getResultList());
    }
}
