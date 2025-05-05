package com.codesoftlution.petnova.tenantmicroservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal priceUsd;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Interval interval;

    @Column(length = 1024)
    private String features;

    public enum Interval { MONTHLY, ANNUAL }

    private LocalDateTime deletedAt;
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}
