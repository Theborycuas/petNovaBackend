package com.codesoftlution.petNova.office_microservice.controllers;

import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import com.codesoftlution.petNova.office_microservice.services.OfficeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("apiPetNova/offices")
public class OfficeController {
    @Autowired
    private OfficeService officeService;

    @Autowired
    HttpServletRequest request;

    Logger log = Logger.getLogger(OfficeController.class.getName());

    @RequestMapping(value = "/resgisterOffice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity resgisterOffice(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody OfficeModel officeModel
    ) {
        try {
            log.info("START OFFICE REGISTER");
            OfficeModel savedOffice = officeService.officeRegister(token, officeModel);
            log.info("END OFFICE REGISTER");
            return new ResponseEntity(savedOffice, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/listAllOffice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAllOffice() {
        log.info("START OFFICE LIST ALL OFICE");
        List<OfficeModel> officeModelList = officeService.getAllOffices();
        log.info("END OFFICE LIST ALL OFICE");
        return new ResponseEntity<>(officeModelList, HttpStatus.OK);

    }

    @RequestMapping(value = "/getOfficeById/{officeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOfficeById(@PathVariable Long officeId) {
        try {
            log.info("START OFFICE GET OFICE BY ID: ");
            Optional<OfficeModel> officeModel = Optional.ofNullable(officeService.getOfficeById(officeId)
                    .orElseThrow(() -> new RuntimeException("Consultorio no encontrado")));
            log.info("END OFFICE GET OFICE BY ID: ");
            return new ResponseEntity(officeModel, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/updateOfficeById/{officeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOfficeById(
            @Valid @PathVariable Long officeId,
            @Valid @RequestParam Long userId,
            @Valid @RequestBody OfficeModel officeModel) {
        try {
            log.info("START OFFICE UPDATE BY ID");
            OfficeModel officeModelEncontrado = officeService.updateOffice(officeId, userId, officeModel);
            log.info("END OFFICE UPDATE BY ID");
            return new ResponseEntity(officeModelEncontrado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "deleteOfficeById/{officeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOfficeById(
            @Valid @PathVariable Long officeId,
            @Valid @RequestParam Long userId
    ) {
        try {
            log.info("START OFFICE DELETE BY ID");
            OfficeModel officeModelEncontrado = officeService.deleteOffice(officeId, userId);
            log.info("END OFFICE DELETE BY ID");
            return new ResponseEntity(officeModelEncontrado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
