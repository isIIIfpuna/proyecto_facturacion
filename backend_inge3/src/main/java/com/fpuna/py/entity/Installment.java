package com.fpuna.py.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "installments")
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "installments_id_gen")
    @SequenceGenerator(name = "installments_id_gen", sequenceName = "installments_installment_id_seq", allocationSize = 1)
    @Column(name = "installment_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @NotNull
    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @ColumnDefault("false")
    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}