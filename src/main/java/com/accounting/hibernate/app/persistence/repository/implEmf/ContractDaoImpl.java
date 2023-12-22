package com.accounting.hibernate.app.persistence.repository.implEmf;

import com.accounting.hibernate.app.persistence.model.Contract;
import com.accounting.hibernate.app.persistence.repository.ContractDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ContractDaoImpl implements ContractDao {
    private EntityManagerFactory entityManagerFactory;

    public ContractDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Contract contract) {
        performWithinPersistenceContext(entityManager -> entityManager.persist(contract));
    }

    @Override
    public void update(Contract contract) {
        performWithinPersistenceContext(entityManager -> entityManager.merge(contract));
    }

    @Override
    public void delete(Contract contract) {
        performWithinPersistenceContext(entityManager -> {
            Contract contractMerged = entityManager.merge(contract);
            entityManager.remove(contractMerged);
        });
    }

    @Override
    public Contract getById(Long id) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager.find(Contract.class, id));
    }

    @Override
    public List<Contract> getAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select c from Contract c", Contract.class).getResultList());
    }

    private void performWithinPersistenceContext(Consumer<EntityManager> entityManagerConsumer) {
        performReturningWithinPersistenceContext(entityManager -> {
            entityManagerConsumer.accept(entityManager);
            return null;
        });
    }

    private <T> T performReturningWithinPersistenceContext(Function<EntityManager, T> entityManagerFunction) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        T result;

        try {
            result = entityManagerFunction.apply(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("rolled back. ", e);
        } finally {
            entityManager.close();
        }

        return result;
    }
}
