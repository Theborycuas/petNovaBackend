package com.codesoftlution.petnova.tenantmicroservice.interfaces;

import com.codesoftlution.petnova.tenantmicroservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petnova.tenantmicroservice.config.JwtUtil;
import com.codesoftlution.petnova.tenantmicroservice.dtos.CreateTenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.repositories.ITenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ITenantService {

    TenantModel createTenant(String token, CreateTenantDTO createTenantDTO);

    List<TenantModel> getAllTenants();

    Optional<TenantModel> getTenantById(Long id);

    boolean deleteTenantById(String token, Long tenantId);
}
