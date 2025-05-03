package com.codesoftlution.petnova.tenantmicroservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Getter
@Setter
@Entity
@Table(name = "tenants")
public class TenantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantName;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    private String contactPhone;
    private String address;

    // Suscripción y facturación
    @Column(nullable = false)
    private Long planId;

    @Column(nullable = false)
    private LocalDate subscriptionStartDate;

    @Column(nullable = false)
    private LocalDate subscriptionEndDate;

    @Column(nullable = false)
    private String billingCycle;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean emailVerified = false;

    // Configuración multi-tenant
    private String timeZone;
    private String preferredLanguage;

    @Column(columnDefinition = "TEXT")
    private String featureFlags;               // JSON string

    @Column(columnDefinition = "TEXT")
    private String metadata;                   // JSON libre

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime deletedAt;
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}

