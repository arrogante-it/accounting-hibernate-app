package com.accounting.hibernate.app.persistence.repository.impl;

import com.accounting.hibernate.app.persistence.model.Payment;
import com.accounting.hibernate.app.persistence.repository.PaymentDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class PaymentDaoImpl implements PaymentDao {
    private final EntityManagerFactory entityManagerFactory;

    public PaymentDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Payment payment) {
        performWithinPersistenceContext(entityManager -> entityManager.persist(payment));
    }

    @Override
    public void update(Payment payment) {
        performWithinPersistenceContext(entityManager -> entityManager.merge(payment));
    }

    @Override
    public void delete(Payment payment) {
        performWithinPersistenceContext(entityManager -> {
            Payment paymentMerged = entityManager.merge(payment);
            entityManager.remove(paymentMerged);
        });
    }

    @Override
    public Payment getById(Long id) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select p from Payment p join fetch p.contract " +
                        "where p.id = :id", Payment.class)
                .setParameter("id", id)
                .getSingleResult()
        );

    }

    @Override
    public List<Payment> getAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select p from Payment p join fetch p.contract", Payment.class))
                .getResultList();
    }

    @Override
    public List<Payment> findAllByContract(Long contractId) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select p from Payment p join fetch p.contract " +
                        "where p.contract.id = :contractId", Payment.class)
                        .setParameter("contractId", contractId)
                        .getResultList()
        );

    }

    // todo refactor
    // todo rewrite, cos need override lazy
    @Override
    public List<Payment> findAllByCustomer(Long customerId) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select p from Payment p " +
                        "where p.customer_id = :customerId", Payment.class)
                        .setParameter("customerId", customerId)
                        .getResultList()
        );
    }

    // todo refactor
    // todo rewrite, cos need override lazy
    @Override
    public List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select p from Payment p " +
                        "where p.customer_id = :customerId and p.amount > :amount", Payment.class)
                        .setParameter("customerId", customerId)
                        .setParameter("amount", amount)
                        .getResultList()
        );
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
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw new RuntimeException("rolled back. " + e);
        } finally {
            entityManager.close();
        }

        return result;
    }
}
