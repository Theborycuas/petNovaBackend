package com.codesoftlution.petNova.office_microservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "offices")
public class OfficeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long tenantId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "contact_phone", nullable = false, unique = true)
    private String contactPhone;

    @Column(nullable = false, unique = true)
    private String taxId;

    @Column(columnDefinition = "TEXT")
    private String logoUrl;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Long currentPlan;
    @Column(nullable = false)
    private LocalDate subscriptionStartDate;
    @Column(nullable = false)
    private LocalDate subscriptionEndDate;

    private String preferredLanguage;
    private String timeZone;
    private boolean allowsOnlineBooking;
    private String officeHours;
    private String locationCoordinates;

    private LocalDateTime deletedAt;
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

}
