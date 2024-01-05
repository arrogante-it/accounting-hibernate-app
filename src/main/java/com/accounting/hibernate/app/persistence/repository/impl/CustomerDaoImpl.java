package com.accounting.hibernate.app.persistence.repository.impl;

import com.accounting.hibernate.app.persistence.model.Customer;
import com.accounting.hibernate.app.persistence.repository.CustomerDao;
import com.accounting.hibernate.app.persistence.util.EntityManagerUtil;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerDaoImpl implements CustomerDao {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Customer customer) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(entityManager -> entityManager.persist(customer));
    }

    @Override
    public void update(Customer customer) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(entityManager -> entityManager.merge(customer));
    }

    @Override
    public void delete(Customer customer) {
        new EntityManagerUtil(entityManagerFactory)
                .performWithinPersistenceContext(entityManager -> {
                    Customer mergedCustomer = entityManager.merge(customer);
                    entityManager.remove(mergedCustomer);
                });
    }

    @Override
    public Customer getById(Long id) {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(entityManager ->
                        entityManager.createQuery(
                                "select distinct c from Customer c left join fetch c.contracts where c.id = :customerId",
                                Customer.class)
                                .setParameter("customerId", id)
                                .getSingleResult());
    }

    @Override
    public List<Customer> getAll() {
        return new EntityManagerUtil(entityManagerFactory)
                .performReturningWithinPersistenceContext(entityManager ->
                        entityManager.createQuery("select distinct c from Customer c left join fetch c.contract",
                                Customer.class)
                                .getResultList());
    }
}
