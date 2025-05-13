package com.codesoftlution.petnova.tenantmicroservice.mapers;

import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.dtos.UserPublicDTO;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TenantMappers {

    public static TenantModel toTenantModel(final TenantDTO tenantDTO) {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("Zona horaria actual: " + zoneId);

        TenantModel tenantModel = new TenantModel();

        tenantModel.setTenantName(tenantDTO.getTenantName());
        tenantModel.setContactEmail(tenantDTO.getContactEmail());
        tenantModel.setContactPhone(tenantDTO.getContactPhone());
        tenantModel.setAddress(tenantDTO.getAddress());
        tenantModel.setCity(tenantDTO.getCity());
        tenantModel.setPlanId(tenantDTO.getPlanId());
        tenantModel.setCurrency(tenantDTO.getCurrency());

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

    public static TenantDTO toTenantDTO(final TenantModel tenantModel) {

        TenantDTO tenant = new TenantDTO();

        tenant.setTenantName(tenantModel.getTenantName());
        tenant.setContactEmail(tenantModel.getContactEmail());
        tenant.setContactPhone(tenantModel.getContactPhone());
        tenant.setAddress(tenantModel.getAddress());
        tenant.setCity(tenantModel.getCity());
        tenant.setPlanId(tenantModel.getPlanId());
        tenant.setCurrency(tenantModel.getCurrency());
        return tenant;

    }
}
