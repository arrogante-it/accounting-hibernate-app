package com.accounting.hibernate.app.persistence.util;

import static com.accounting.hibernate.app.persistence.constants.ExceptionConstants.ROLLED_BACK;
import com.accounting.hibernate.app.persistence.exception.HQLOperationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
public class EntityManagerUtil {
    private EntityManagerFactory entityManagerFactory;

    public void performWithinPersistenceContext(Consumer<EntityManager> entityManagerConsumer) {
        performReturningWithinPersistenceContext(entityManager -> {
            entityManagerConsumer.accept(entityManager);
            return null;
        });
    }

    public <T> T performReturningWithinPersistenceContext(Function<EntityManager, T> entityManagerFunction) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        T result;

        try {
            result = entityManagerFunction.apply(entityManager);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw new HQLOperationException(ROLLED_BACK + e);
        } finally {
            entityManager.close();
        }

        return result;
    }
}
