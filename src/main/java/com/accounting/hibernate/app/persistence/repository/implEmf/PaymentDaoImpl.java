package com.accounting.hibernate.app.persistence.repository.implEmf;

import com.accounting.hibernate.app.persistence.model.Payment;
import com.accounting.hibernate.app.persistence.repository.PaymentDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
                entityManager.find(Payment.class, id));
    }

    @Override
    public List<Payment> getAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select p from Payment p", Payment.class)).getResultList();
    }

    @Override
    public List<Payment> findAllByContract(Long contractId) {
        return null;
    }

    @Override
    public List<Payment> findAllByCustomer(Long customerId) {
        return null;
    }

    @Override
    public List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount) {
        return null;
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
