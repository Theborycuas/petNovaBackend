package com.codesoftlution.petnova.tenantmicroservice.mapers;

import com.codesoftlution.petnova.tenantmicroservice.dtos.CreateTenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TenantMappers {

    public static TenantModel toTenantModel(final CreateTenantDTO createTenantDTO) {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("Zona horaria actual: " + zoneId);

        TenantModel tenantModel = new TenantModel();

        tenantModel.setTenantName(createTenantDTO.getTenantName());
        tenantModel.setContactEmail(createTenantDTO.getContactEmail());
        tenantModel.setContactPhone(createTenantDTO.getContactPhone());
        tenantModel.setAddress(createTenantDTO.getAddress());
        tenantModel.setCity(createTenantDTO.getCity());
        tenantModel.setPlanId(createTenantDTO.getPlanId());
        tenantModel.setCurrency(createTenantDTO.getCurrency());

        tenantModel.setSubscriptionStartDate(LocalDate.now());
        tenantModel.setSubscriptionEndDate(LocalDate.now().plusMonths(1)); // ejemplo: 1 mes de duración
        tenantModel.setBillingCycle("MONTHLY");
        tenantModel.setActive(false);
        tenantModel.setEmailVerified(false);

        tenantModel.setTimeZone(String.valueOf(zoneId));
        tenantModel.setPreferredLanguage("ES");

        tenantModel.setCreatedAt(LocalDateTime.now());

        return tenantModel;

    }
}
