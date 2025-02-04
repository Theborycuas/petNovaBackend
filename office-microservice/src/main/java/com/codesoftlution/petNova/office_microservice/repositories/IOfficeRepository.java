package com.codesoftlution.petNova.office_microservice.repositories;

import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOfficeRepository extends JpaRepository<OfficeModel, Long> {
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
}
