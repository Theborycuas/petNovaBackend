package com.codesoftlution.petNova.office_microservice.repositories;

import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOfficeRepository extends JpaRepository<OfficeModel, Long> {
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);

    List<OfficeModel> findByTenantId(Long tenantId);
}
