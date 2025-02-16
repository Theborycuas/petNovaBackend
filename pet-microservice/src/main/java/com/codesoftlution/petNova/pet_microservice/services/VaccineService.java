package com.codesoftlution.petNova.pet_microservice.services;

import com.codesoftlution.petNova.pet_microservice.models.PetModel;
import com.codesoftlution.petNova.pet_microservice.models.VaccineModel;
import com.codesoftlution.petNova.pet_microservice.repositories.IPetRepository;
import com.codesoftlution.petNova.pet_microservice.repositories.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {
    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IPetRepository petRepository;

    public VaccineModel saveVacciones(VaccineModel vaccineModel) {
        return iVaccineRepository.save(vaccineModel);
    }

    public VaccineModel getVaccineById(Long id) {
        return iVaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada"));
    }

    public List<VaccineModel> getAllVaccines(){
        return iVaccineRepository.findAll();
    }

    public List<VaccineModel> findByPetModel(Long petId) {
        PetModel petModel = petRepository.findById(petId).
                orElseThrow(() -> new RuntimeException("MASCOTA NO ENCONTRADA"));
        return iVaccineRepository.findByPetModel(petModel);
    }

    public List<VaccineModel> findByVeterinarianId(Long veterinarianId) {
        return iVaccineRepository.findByVeterinarianId(veterinarianId);
    }

    public List<VaccineModel> findBypetModelAndAplicationDateBetween(
            Long petId, LocalDateTime startDate, LocalDateTime endDate) {
        PetModel petModel = petRepository.findById(petId).
                orElseThrow(()-> new RuntimeException("MASCOTA NO ENCONTRADA"));
        return iVaccineRepository.findBypetModelAndAplicationDateBetween(
                petModel, startDate, endDate);
    }

    public VaccineModel updateVaccine(Long vaccineId, VaccineModel vaccineUpdate) {

        VaccineModel vaccineFound = getVaccineById(vaccineId);

        Optional.ofNullable(vaccineUpdate.getVaccineName()).ifPresent(vaccineFound::setVaccineName);
        Optional.ofNullable(vaccineUpdate.getAplicationDate()).ifPresent(vaccineFound::setAplicationDate);
        Optional.ofNullable(vaccineUpdate.getNextAplicationDate()).ifPresent(vaccineFound::setNextAplicationDate);
        Optional.ofNullable(vaccineUpdate.getPetModel()).ifPresent(vaccineFound::setPetModel);
        Optional.ofNullable(vaccineUpdate.getVeterinarianId()).ifPresent(vaccineFound::setVeterinarianId);
        Optional.ofNullable(vaccineUpdate.getMedicalHId()).ifPresent(vaccineFound::setMedicalHId);

        return iVaccineRepository.save(vaccineFound);
    }

    public void deleteVaccine(Long vaccineId) {
        VaccineModel vaccineFound = getVaccineById(vaccineId);

        vaccineFound.setActive(false);
        iVaccineRepository.save(vaccineFound);
    }
}
