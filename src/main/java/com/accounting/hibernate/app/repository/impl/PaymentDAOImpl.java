package com.accounting.hibernate.app.repository.impl;

import com.accounting.hibernate.app.model.Payment;
import com.accounting.hibernate.app.repository.PaymentDAO;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class PaymentDAOImpl implements PaymentDAO {

    private final SessionFactory sessionFactory;

    @Override
    public Payment getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Payment.class, id);
        }
    }

    @Override
    public List<Payment> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Payment", Payment.class).list();
        }
    }

    @Override
    public void save(Payment payment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(payment);
            transaction.commit();
        }
    }

    @Override
    public void delete(Payment payment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(payment);
            transaction.commit();
        }
    }

    @Override
    public void update(Payment payment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(payment);
            transaction.commit();
        }
    }

    @Override
    public List<Payment> findAllByContract(Long contractId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Payment p where p.contract.id = :contractId";
            Query<Payment> query = session.createQuery(hql, Payment.class);
            query.setParameter("contractId", contractId);
            return query.list();
        }
    }

    @Override
    public List<Payment> findAllByCustomer(Long customerId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Payment p where p.contract.customer.id = :customerId";
            Query<Payment> query = session.createQuery(hql, Payment.class);
            query.setParameter("customerId", customerId);
            return query.list();
        }
    }

    @Override
    public List<Payment> findAllAmountMoreThan(Long customerId, BigDecimal amount) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Payment p where p.contract.customer.id = :customerId and p.amount > :amount";
            Query<Payment> query = session.createQuery(hql, Payment.class);
            query.setParameter("customerId", customerId);
            query.setParameter("amount", amount);
            return query.list();
        }
    }
}
