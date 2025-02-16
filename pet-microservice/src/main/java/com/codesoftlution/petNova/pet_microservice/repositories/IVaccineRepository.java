package com.codesoftlution.petNova.pet_microservice.repositories;

import com.codesoftlution.petNova.pet_microservice.models.PetModel;
import com.codesoftlution.petNova.pet_microservice.models.VaccineModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IVaccineRepository extends JpaRepository<VaccineModel, Long> {
    List<VaccineModel> findByPetModel(PetModel pet);
    List<VaccineModel> findByVeterinarianId(Long veterinarianId);
    List<VaccineModel> findBypetModelAndAplicationDateBetween(
            PetModel petModel, LocalDateTime startDate, LocalDateTime endDate);
}
