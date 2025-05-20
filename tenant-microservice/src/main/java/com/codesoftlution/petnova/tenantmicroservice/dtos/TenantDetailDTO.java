package com.codesoftlution.petnova.tenantmicroservice.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TenantDetailDTO {

    private Long id;
    private String tenantName;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String city;
    private Long planId;
    private List<Long> managerIds;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private String billingCycle;
    private String currency;
    private boolean active;
    private boolean emailVerified = false;
    private String timeZone;
    private String preferredLanguage;
    private String featureFlags;
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
