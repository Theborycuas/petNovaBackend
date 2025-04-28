package com.codesoftlution.petnova.tenantmicroservice.repositories;

import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITenantRepository extends JpaRepository<TenantModel, Long> {
}
