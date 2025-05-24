package com.codesoftlution.petNova.office_microservice.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfficeDetailsDTO {

    private Long id;
    private Long tenantId;
    private String name;
    private String address;
    private String city;
    private String contactPhone;
    private String taxId;
    private String logoUrl;
    private boolean active;
    private String contactEmail;
    private boolean emailVerified;
    private List<Long> managerIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long currentPlan;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private String preferredLanguage;
    private String timeZone;
    private boolean allowsOnlineBooking;
    private String officeHours;
    private String locationCoordinates;
}
