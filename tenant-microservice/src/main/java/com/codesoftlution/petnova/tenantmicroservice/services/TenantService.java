package com.codesoftlution.petnova.tenantmicroservice.services;

import com.codesoftlution.petnova.tenantmicroservice.config.JwtUtil;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TenantService {
    @Autowired
    ITenantRepository tenantRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public TenantModel createTenant(TenantModel tenantModel) {

            tenantModel.setCreatedAt(LocalDateTime.now());
            tenantModel.setSubscriptionStartDate(LocalDate.now());
            tenantModel.setSubscriptionEndDate(LocalDate.now().plusDays(30));
            tenantModel.setBillingCycle("MONTHLY");
            tenantModel.setActive(false);
            tenantModel.setEmailVerified(false);
            return tenantRepository.save(tenantModel);
    }

    public List<TenantModel> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Optional<TenantModel> getTenantById(Long id) {
        return tenantRepository.findById(id);
    }

    public TenantModel deleteTenantById(Long tenantId) {

        TenantModel tenantFound = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        tenantFound.softDelete();

        return tenantRepository.save(tenantFound);

    }
}
