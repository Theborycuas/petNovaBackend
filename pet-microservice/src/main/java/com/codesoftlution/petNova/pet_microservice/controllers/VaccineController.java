package com.codesoftlution.petNova.pet_microservice.controllers;

import com.codesoftlution.petNova.pet_microservice.models.VaccineModel;
import com.codesoftlution.petNova.pet_microservice.services.VaccineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("apiPetNova/vaccines")
@CrossOrigin("*")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    Logger log = Logger.getLogger(this.getClass().getName());

    @RequestMapping(value = "/vaccineRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> vaccineRegister(
            @Valid @RequestBody VaccineModel vaccineModel
    ) {
        try {
            log.info("START VACCINE REGISTER");
            vaccineService.saveVacciones(vaccineModel);
            log.info("END VACCINE REGISTER");
            return new ResponseEntity<>("VACUNA REGISTRADA", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getAllVaccines", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllVaccines() {
        try {
            log.info("START GET ALL VACCINES");
            List<VaccineModel> vaccinesFound = vaccineService.getAllVaccines();
            log.info("END GET ALL VACCINES");
            return new ResponseEntity<>(vaccinesFound, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/findByPetModel/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByPetModel(@Valid @PathVariable("petId") Long petId
    ) {
        try {
            log.info("START GET FIND VACCINE BY PET ID");
            List<VaccineModel> vaccinesFound = vaccineService.findByPetModel(petId);
            log.info("END GET FIND VACCINE BY PET ID");
            return new ResponseEntity<>(vaccinesFound, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/findByVeterinarianId/{veterinarioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByVeterinarianId(@Valid @PathVariable("veterinarioId") Long veterinarioId) {
        try {
            log.info("START FIND VACCINE BY VTERINARIAN ID");
            List<VaccineModel> vaccinesFound = vaccineService.findByVeterinarianId(veterinarioId);
            log.info("END FIND VACCINE BY VTERINARIAN ID");
            return new ResponseEntity<>(vaccinesFound, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/findVaccineByPetIdAndDate/{petId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findVaccineByPetIdAndDate(
            @Valid @PathVariable("petId") Long petId,
            @Valid @RequestParam(value = "startDate", required = true) LocalDateTime startDate,
            @Valid @RequestParam(value = "endDate", required = true) LocalDateTime endDate
    ) {
        try {
            log.info("START FIND VACCINE BY PET ID AND DATE");
            List<VaccineModel> vaccinesFound = vaccineService.findBypetModelAndAplicationDateBetween(
                    petId, startDate, endDate
            );
            log.info("END FIND VACCINE BY PET ID AND DATE");
            return new ResponseEntity<>(vaccinesFound, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateVaccine/{vaccineId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateVaccine(
            @Valid @PathVariable("vaccineId") Long vaccineId,
            @Valid @RequestBody VaccineModel vaccineModel
    ) {
        try {
            log.info("START UPDATE VACCINE BY ID");
            vaccineService.updateVaccine(vaccineId, vaccineModel);
            log.info("END UPDATE VACCINE BY ID");
            return new ResponseEntity<>("VACUNA ACTUALIZADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteVaccine/{vaccineId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteVaccine(
            @Valid @PathVariable("vaccineId") Long vaccineId
    ) {
        try {
            log.info("START DELETE VACCINE BY ID");
            vaccineService.deleteVaccine(vaccineId);
            log.info("END DELETE VACCINE BY ID");
            return new ResponseEntity<>("VACUNA ELIMINADA", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
