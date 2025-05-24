package com.codesoftlution.petNova.office_microservice.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDTO {

    private Long tenantId;
    private String name;
    private String address;
    private String city;
    private String contactPhone;
    private String taxId;
    private String logoUrl;
    private String contactEmail;
    private Long currentPlan;
    private List<Long> managerIds;

}
