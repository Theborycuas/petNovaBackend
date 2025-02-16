package com.codesoftlution.petNova.medicalHistory_microservice.controllers;

import com.codesoftlution.petNova.medicalHistory_microservice.models.MedicalHistoryModel;
import com.codesoftlution.petNova.medicalHistory_microservice.repositories.IMedicalHistoryRepository;
import com.codesoftlution.petNova.medicalHistory_microservice.services.MedicalHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("apiPetNova/medicalHistories")
@CrossOrigin("*")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private IMedicalHistoryRepository medicalHistoryRepository;



    Logger log = Logger.getLogger(MedicalHistoryController.class.getName());

    @RequestMapping(value = "/registerMedicalH", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity registerMedicalH(
            @Valid @RequestHeader("Authorization") String token,
            @Valid @RequestBody MedicalHistoryModel medicalHistoryModel) {
        try {
            log.info("START REGISTER MEDICAL HISTORY");
            MedicalHistoryModel medicalHistoryToSave = medicalHistoryService
                    .saveMedicalH(token, medicalHistoryModel);
            log.info("END REGISTER MEDICAL HISTORY");
            return new ResponseEntity(medicalHistoryToSave, HttpStatus.CREATED);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getMedicalHById/{medicalHId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity getMedicalHById(@Valid @PathVariable("medicalHId") Long medicalHId) {
        try {
            log.info("START GET MEDICAL HISTORY BY ID");
            MedicalHistoryModel medicalHistoryModel = medicalHistoryService.getMedicalHById(medicalHId);
            log.info("END GET MEDICAL HISTORY BY ID");
            return new ResponseEntity(medicalHistoryModel, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getMedicalHByPetId/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity getMedicalHByPetId(@Valid @PathVariable("petId") Long petId) {
        try {
            log.info("START GET MEDICAL HISTORY BY PET ID");
            List<MedicalHistoryModel> medicalHistoryModelList = medicalHistoryRepository.findByPetId(petId);
            log.info("END GET MEDICAL HISTORY BY PET ID");
            return new ResponseEntity(medicalHistoryModelList, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @RequestMapping(value = "/getMedicalHByVeterinarioId/{veterinarioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity getMedicalHByVeterinarioId(@Valid @PathVariable("veterinarioId") Long veterinarioId) {
        try {
            log.info("START GET MEDICAL HISTORY BY VTERINARIO ID");
            List<MedicalHistoryModel> medicalHistoryModelList = medicalHistoryRepository.findByVeterinarioId(veterinarioId);
            log.info("END GET MEDICAL HISTORY BY VTERINARIO ID");
            return new ResponseEntity(medicalHistoryModelList, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateMedicalH/{medicalHId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity updateMedicalH(
            @Valid @PathVariable("medicalHId") Long medicalHId,
            @Valid @RequestBody MedicalHistoryModel medicalHistoryUpdate
    ){
        try {
            log.info("START UPDATE MEDICAL HISTORY");
            medicalHistoryService.updateMedicalH(medicalHId, medicalHistoryUpdate);
            log.info("END UPDATE MEDICAL HISTORY");
            return new ResponseEntity("HISTORIAL MEDICO ACTUALIZADO", HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }



    @RequestMapping(value = "/deleteMedicalHById/{medicalHId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity deleteMedicalHById(@Valid @PathVariable("medicalHId") Long medicalHId) {
        try {
            log.info("START DELETE MEDICAL HISTORY BY ID");
            medicalHistoryService.deleteMedicalH(medicalHId);
            log.info("END DELETE MEDICAL HISTORY BY ID");
            return new ResponseEntity("HISTORIA MEDICA ELIMINADA", HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
