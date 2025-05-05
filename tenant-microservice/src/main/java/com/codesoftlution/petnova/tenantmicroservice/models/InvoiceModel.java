package com.codesoftlution.petnova.tenantmicroservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "invoices")
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subscription_id")
    private SubscriptionModel subscription;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate issuedDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate paidDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String notes;

    public enum Status { ISSUED, PAID, FAILED }
}
