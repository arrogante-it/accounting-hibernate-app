package com.accounting.hibernate.app.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // для toMany делаем private
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "sum", nullable = false)
    private int sum;

    @Column(name = "comments")
    private String comments;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY) // по умолчанию стоит eager для всех ToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

//    @OneToMany(mappedBy = "contract")
//    List<Payment> payments = new ArrayList<>();
}
