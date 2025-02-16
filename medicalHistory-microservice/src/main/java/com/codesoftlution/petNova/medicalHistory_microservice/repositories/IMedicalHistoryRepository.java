package com.codesoftlution.petNova.medicalHistory_microservice.repositories;

import com.codesoftlution.petNova.medicalHistory_microservice.models.MedicalHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistoryModel, Long> {
    List<MedicalHistoryModel> findByPetId(Long petId);
    List<MedicalHistoryModel> findByVeterinarioId(Long veterinarioId);
}
