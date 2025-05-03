package com.codesoftlution.petnova.tenantmicroservice.repositories;

import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ITenantRepository extends JpaRepository<TenantModel, Long> {
    List<TenantModel> findAllByDeletedAtIsNull();
}
