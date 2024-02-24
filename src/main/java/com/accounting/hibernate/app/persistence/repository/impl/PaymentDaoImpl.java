package com.accounting.hibernate.app.persistence.repository.impl;

import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_PAYMENTS_BY_CONTRACT_ID_HQL;
import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_PAYMENTS_BY_CUSTOMER_ID_HQL;
import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_PAYMENTS_BY_ID_HQL;
import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_PAYMENTS_HQL;
import static com.accounting.hibernate.app.persistence.constants.AccountConstants.SELECT_ALL_PAYMENTS_MORE_THAN_BY_CUSTOMER_ID_HQL;
import com.accounting.hibernate.app.persistence.model.Payment;
import com.accounting.hibernate.app.persistence.repository.PaymentDao;
import com.accounting.hibernate.app.persistence.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PaymentDaoImpl implements PaymentDao {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Payment payment, EntityManager entityManager) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(em -> entityManager.persist(payment));
    }

    @Override
    public void update(Payment payment, EntityManager entityManager) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(em -> entityManager.merge(payment));
    }

    @Override
    public void delete(Payment payment, EntityManager entityManager) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(em -> {
                    Payment paymentMerged = entityManager.merge(payment);
                    entityManager.remove(paymentMerged);
                });
    }

    @Override
    public Payment getById(Long id, EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_PAYMENTS_BY_ID_HQL, Payment.class)
                                .setParameter("id", id)
                                .getSingleResult()
                );

    }

    @Override
    public List<Payment> getAll(EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_PAYMENTS_HQL, Payment.class))
                .getResultList();
    }

    @Override
    public List<Payment> findAllByContract(Long contractId, EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_PAYMENTS_BY_CONTRACT_ID_HQL, Payment.class)
                                .setParameter("contractId", contractId)
                                .getResultList()
                );

    }

    // todo refactor
    // todo check overriding lazy
    @Override
    public List<Payment> findAllByCustomer(Long customerId, EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_PAYMENTS_BY_CUSTOMER_ID_HQL, Payment.class)
                                .setParameter("customerId", customerId)
                                .getResultList()
                );
    }

    // todo refactor
    // todo check overriding lazy
    @Override
    public List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount, EntityManager entityManager) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(em ->
                        entityManager.createQuery(SELECT_ALL_PAYMENTS_MORE_THAN_BY_CUSTOMER_ID_HQL, Payment.class)
                                .setParameter("customerId", customerId)
                                .setParameter("amount", amount)
                                .getResultList()
                );
    }
}
