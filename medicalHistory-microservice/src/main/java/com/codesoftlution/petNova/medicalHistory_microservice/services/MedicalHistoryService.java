package com.codesoftlution.petNova.medicalHistory_microservice.services;

import com.codesoftlution.petNova.medicalHistory_microservice.clientsfeign.PetFeignClient;
import com.codesoftlution.petNova.medicalHistory_microservice.clientsfeign.UserFeignClient;
import com.codesoftlution.petNova.medicalHistory_microservice.dtos.PetDTO;
import com.codesoftlution.petNova.medicalHistory_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.medicalHistory_microservice.models.MedicalHistoryModel;
import com.codesoftlution.petNova.medicalHistory_microservice.repositories.IMedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicalHistoryService {
    @Autowired
    IMedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PetFeignClient petFeignClient;

    public MedicalHistoryModel saveMedicalH(String token, MedicalHistoryModel medicalHistoryModel) {
        //Buscar mascota
        PetDTO petFound = petFeignClient
                .getPetById("Bearer " + token, medicalHistoryModel.getPetId());

        //Buscar VETERINARIO
        UserDTO userFound = userFeignClient
                .getUserById("Bearer " + token, medicalHistoryModel.getVeterinarioId());

        if(!"VETERINARIO".equalsIgnoreCase(userFound.getRollName())){
            throw new RuntimeException("El Usuario no es VETERINARIO");
        }

        medicalHistoryModel.setPetId(petFound.getId());
        medicalHistoryModel.setVeterinarioId(userFound.getId());
        medicalHistoryModel.setFechaConsulta(LocalDateTime.now());

        return medicalHistoryRepository.save(medicalHistoryModel);
    }

    public MedicalHistoryModel getMedicalHById(Long id) {
        return medicalHistoryRepository.findById(id).orElseThrow(()
                -> new RuntimeException("La Historia Médica no existe"));
    }

    public MedicalHistoryModel updateMedicalH(Long id, MedicalHistoryModel medicalHistoryUpdate) {
        MedicalHistoryModel medicalHistoryFound = getMedicalHById(id);
        //Utilizo Optional.ofNullable reemplazando el if para comparar si cada atributo viene vacio
        Optional.ofNullable(medicalHistoryUpdate.getPetId()).ifPresent(medicalHistoryFound::setPetId);
        Optional.ofNullable(medicalHistoryUpdate.getVeterinarioId()).ifPresent(medicalHistoryFound::setVeterinarioId);
        Optional.ofNullable(medicalHistoryUpdate.getFechaConsulta()).ifPresent(medicalHistoryFound::setFechaConsulta);
        Optional.ofNullable(medicalHistoryUpdate.getMotivoConsulta()).ifPresent(medicalHistoryFound::setMotivoConsulta);
        Optional.ofNullable(medicalHistoryUpdate.getDiagnostico()).ifPresent(medicalHistoryFound::setDiagnostico);
        Optional.ofNullable(medicalHistoryUpdate.getTratamiento()).ifPresent(medicalHistoryFound::setTratamiento);
        Optional.ofNullable(medicalHistoryUpdate.getObservaciones()).ifPresent(medicalHistoryFound::setObservaciones);
        return medicalHistoryRepository.save(medicalHistoryFound);
    }



    public void deleteMedicalH (Long medicalHId){
        MedicalHistoryModel medicalHistoryFound = getMedicalHById(medicalHId);
        medicalHistoryFound.setActive(false);
        medicalHistoryRepository.save(medicalHistoryFound);

    }
}
