package com.accounting.hibernate.app.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private int amount_of_money;

    private Date time;

    @JoinColumn(name = "contract_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contract contract;

    @JoinColumn(name = "card_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
}
