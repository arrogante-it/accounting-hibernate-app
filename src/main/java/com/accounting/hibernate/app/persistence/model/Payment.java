package com.accounting.hibernate.app.persistence.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private int amount_of_money;

    @Column(name = "time", nullable = false)
    private LocalDate time;

//    @JoinColumn(name = "contract_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY) // eager по умолчанию стоит для ToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
