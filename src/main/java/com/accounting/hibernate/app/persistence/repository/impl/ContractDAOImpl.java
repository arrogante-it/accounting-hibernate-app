package com.accounting.hibernate.app.persistence.repository.impl;

import com.accounting.hibernate.app.persistence.model.Contract;
import com.accounting.hibernate.app.persistence.repository.ContractDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class ContractDAOImpl implements ContractDao {

    private final SessionFactory sessionFactory;

    @Override
    public Contract getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Contract.class, id);
        }
    }

    @Override
    public List<Contract> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Contract", Contract.class).list();
        }
    }

    @Override
    public void save(Contract contract) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(contract);
            transaction.commit();
        }
    }

    @Override
    public void update(Contract contract) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(contract);
            transaction.commit();
        }
    }

    @Override
    public void delete(Contract contract) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(contract);
            transaction.commit();
        }
    }
}
