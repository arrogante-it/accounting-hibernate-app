package com.accounting.hibernate.app.persistence.repository.impl;

import com.accounting.hibernate.app.persistence.model.Customer;
import com.accounting.hibernate.app.persistence.repository.CustomerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerDaoImpl implements CustomerDao {
    private EntityManagerFactory entityManagerFactory;

    public CustomerDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Customer customer) {
        performWithinPersistenceContext(entityManager -> entityManager.persist(customer));
    }

    @Override
    public void update(Customer customer) {
        performWithinPersistenceContext(entityManager -> entityManager.merge(customer));
    }

    @Override
    public void delete(Customer customer) {
        performWithinPersistenceContext(entityManager -> {
            Customer mergedCustomer = entityManager.merge(customer);
            entityManager.remove(mergedCustomer);
        });
    }

    @Override
    public Customer getById(Long id) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery(
                        "select distinct c from Customer c left join fetch c.contracts where c.id = :customerId",
                        Customer.class)
                        .setParameter("customerId", id).getSingleResult());
    }

    @Override
    public List<Customer> getAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select distinct c from Customer c left join fetch c.contracts",
                        Customer.class)
                        .getResultList());
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
            throw new RuntimeException("rolled back. " + e);
        } finally {
            entityManager.close();
        }

        return result;
    }
}
