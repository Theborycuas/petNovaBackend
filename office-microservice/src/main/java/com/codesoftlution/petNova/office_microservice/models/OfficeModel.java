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

    @Column(nullable = false, unique = true)
    private String phoneNumber;

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

    @Column(nullable = false)
    private String managerName;

    @Column(nullable = false, unique = true)
    private String managerPhone;

    @Column(nullable = false, unique = true)
    private String managerEmail;

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


    //private String serviceList;

    //private String metadata;
}
