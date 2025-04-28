package com.codesoftlution.petnova.tenantmicroservice.services;

import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TenantService {
    @Autowired
    ITenantRepository tenantRepository;

    public TenantModel createTenant(TenantModel tenantModel) {

        tenantModel.setPlanId(tenantModel.getPlanId());
        tenantModel.setCreatedAt(LocalDateTime.now());
        tenantModel.setSubscriptionStartDate(LocalDate.now());
        tenantModel.setSubscriptionEndDate(LocalDate.now().plusDays(30));
        tenantModel.setBillingCycle("MONTHLY");
        tenantModel.setCurrency("USD");
        tenantModel.setStatus(false);
        tenantModel.setEmailVerified(false);

        return tenantRepository.save(tenantModel);

    }
}
