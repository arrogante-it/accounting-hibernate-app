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

        Customer customer1 = new Customer(3L, "Harry", "Wall Street");
        Contract contract1 = new Contract("MMM1", "Subject", 100, "comments", LocalDate.parse("2023-12-27"));
        Contract contract2 = new Contract("General Dynamics", "Subject2", 150, "comments2", LocalDate.parse("2024-01-02"), customer1);
        Payment payment1 = new Payment(101, LocalDate.parse("2023-12-26"), contract1);
        Payment payment2 = new Payment(500, LocalDate.parse("2024-01-02"), contract2);

        customerDao.save(customer1);
        contractDao.save(contract1);
        contractDao.save(contract2);
        paymentDao.save(payment1);
        paymentDao.save(payment2);

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        List<Payment> list = paymentDao.getAll();

        list.forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.flush();

        entityManager.close();
        emf.close();
    }
}