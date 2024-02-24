package com.accounting.hibernate.app;

import com.accounting.hibernate.app.persistence.model.Contract;
import com.accounting.hibernate.app.persistence.model.Customer;
import com.accounting.hibernate.app.persistence.model.Payment;
import com.accounting.hibernate.app.persistence.repository.ContractDao;
import com.accounting.hibernate.app.persistence.repository.CustomerDao;
import com.accounting.hibernate.app.persistence.repository.PaymentDao;
import com.accounting.hibernate.app.persistence.repository.impl.ContractDaoImpl;
import com.accounting.hibernate.app.persistence.repository.impl.CustomerDaoImpl;
import com.accounting.hibernate.app.persistence.repository.impl.PaymentDaoImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AccountingHibernateApp");
        CustomerDao customerDao = new CustomerDaoImpl(emf);
        ContractDao contractDao = new ContractDaoImpl(emf);
        PaymentDao paymentDao = new PaymentDaoImpl(emf);

        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();

            Customer customer1 = new Customer(3L, "Harry", "Wall Street");
            Contract contract1 = new Contract("MMM1", "Subject", 100, "comments", LocalDate.parse("2023-12-27"));
            Contract contract2 = new Contract("General Dynamics", "Subject2", 150, "comments2", LocalDate.parse("2024-01-02"), customer1);
            Payment payment1 = new Payment(101, LocalDate.parse("2023-12-26"), contract1);
            Payment payment2 = new Payment(500, LocalDate.parse("2024-01-02"), contract2);

            customerDao.save(customer1, entityManager);
            contractDao.save(contract1, entityManager);
            contractDao.save(contract2, entityManager);
            paymentDao.save(payment1, entityManager);
            paymentDao.save(payment2, entityManager);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // rollback transaction if we use hibernate 5.0 or higher
        }

        EntityManager entityManager = emf.createEntityManager();

        List<Payment> list = paymentDao.getAll(entityManager);
        list.forEach(System.out::println);

    }
}