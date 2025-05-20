package com.codesoftlution.petnova.tenantmicroservice.mapers;

import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDetailDTO;
import com.codesoftlution.petnova.tenantmicroservice.dtos.UserPublicDTO;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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

    public static TenantDetailDTO toTenantDetailDTO(final TenantModel model, final List<Long> managerIds) {

        TenantDetailDTO dto = new TenantDetailDTO();
        dto.setId(model.getId());
        dto.setTenantName(model.getTenantName());
        dto.setContactEmail(model.getContactEmail());
        dto.setContactPhone(model.getContactPhone());
        dto.setAddress(model.getAddress());
        dto.setCity(model.getCity());
        dto.setManagerIds(managerIds);
        dto.setPlanId(model.getPlanId());
        dto.setSubscriptionStartDate(model.getSubscriptionStartDate());
        dto.setSubscriptionEndDate(model.getSubscriptionEndDate());
        dto.setBillingCycle(model.getBillingCycle());
        dto.setCurrency(model.getCurrency());
        dto.setActive(model.isActive());
        dto.setEmailVerified(model.isEmailVerified());
        dto.setTimeZone(model.getTimeZone());
        dto.setPreferredLanguage(model.getPreferredLanguage());
        dto.setFeatureFlags(model.getFeatureFlags());
        dto.setMetadata(model.getMetadata());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        return dto;
    }
}
