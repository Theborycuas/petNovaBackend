package com.codesoftlution.petnova.tenantmicroservice.interfaces;

import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ITenantService {

    TenantModel createTenant(String token, TenantDTO tenantDTO);

    List<TenantModel> getAllTenants();

    Optional<TenantModel> getTenantById(Long id);

    TenantModel updateTenantById(String token, Long id, TenantDTO tenantDTO);

    boolean deleteTenantById(String token, Long tenantId);

    TenantDTO getBasicTenantById(String token, Long id);
}
