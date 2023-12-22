package com.accounting.hibernate.app.persistence.repository.implEmf;

import com.accounting.hibernate.app.persistence.model.Customer;
import com.accounting.hibernate.app.persistence.repository.CustomerDao;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerDaoImpl implements CustomerDao {
    private EntityManagerFactory entityManagerFactory;

    public CustomerDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    // todo этого метода нет в ТЗ
    public Customer findByIdFetchContracts(Long id) {
        return readWithinTx(entityManager ->
                entityManager
                        .createQuery("select c from Customer c join fetch c.contracts where c.id = :id",
                                Customer.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    // todo этого метода нет в ТЗ
    private <T> T readWithinTx(Function<EntityManager, T> entityManagerFunction) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.unwrap(Session.class).setDefaultReadOnly(true);
        entityManager.getTransaction().begin();

        try {
            T queryResult = entityManagerFunction.apply(entityManager);
            entityManager.getTransaction().commit();
            return queryResult;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
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
        return performReturningWithinPersistenceContext(entityManager -> entityManager.find(Customer.class, id));
    }

    @Override
    public List<Customer> getAll() {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select c from Customer c", Customer.class).getResultList());
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
