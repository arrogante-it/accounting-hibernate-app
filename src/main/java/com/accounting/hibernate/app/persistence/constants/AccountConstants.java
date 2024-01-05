package com.accounting.hibernate.app.persistence.constants;

public class AccountConstants {
    public static final String SELECT_ALL_CONTRACTS_BY_ID_HQL = "select distinct c from Contract c " +
            "join fetch c.customer " +
            "where c.id = :id ";
    public static final String SELECT_ALL_CONTRACTS_HQL = "select distinct c from Contract c " +
            "join fetch c.customer ";

    public static final String SELECT_ALL_CUSTOMERS_BY_ID_HQL = "select distinct c from Customer c " +
            "left join fetch c.contracts " +
            "where c.id = :customerId ";
    public static final String SELECT_ALL_CUSTOMERS_HQL = "select distinct c from Customer c " +
            "left join fetch c.contracts ";

    public static final String SELECT_ALL_PAYMENTS_BY_ID_HQL = "select distinct p from Payment p " +
            "join fetch p.contract " +
            "where p.id = :id ";
    public static final String SELECT_ALL_PAYMENTS_HQL = "select distinct p from Payment p " +
            "join fetch p.contract ";
    public static final String SELECT_ALL_PAYMENTS_BY_CONTRACT_ID_HQL = "select distinct p from Payment p " +
            "join fetch p.contract " +
            "where p.contract.id = :contractId ";
    public static final String SELECT_ALL_PAYMENTS_BY_CUSTOMER_ID_HQL = "select distinct p from Payment p " +
            "left join fetch p.customer " +
            "where p.customer_id = :customerId ";
    public static final String SELECT_ALL_PAYMENTS_MORE_THAN_BY_CUSTOMER_ID_HQL = "select distinct p from Payment p " +
            "join fetch p.customer " +
            "where p.customer_id = :customerId " +
            "and p.amount > :amount ";
}
