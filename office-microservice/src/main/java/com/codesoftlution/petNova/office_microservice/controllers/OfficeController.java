package com.codesoftlution.petNova.office_microservice.controllers;

import com.codesoftlution.petNova.office_microservice.dtos.OfficeDTO;
import com.codesoftlution.petNova.office_microservice.dtos.OfficeDetailsDTO;
import com.codesoftlution.petNova.office_microservice.models.OfficeModel;
import com.codesoftlution.petNova.office_microservice.services.OfficeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.codesoftlution.petNova.office_microservice.mapers.OfficeMappers.toOfficeDetailsDTO;

@RestController
@RequestMapping("apiPetNova/offices")
public class OfficeController {
    @Autowired
    private OfficeService officeService;

    @Autowired
    HttpServletRequest request;

    Logger log = Logger.getLogger(OfficeController.class.getName());


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/resgisterOffice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resgisterOffice(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody OfficeModel officeModel
    ) {
        try {
            log.info("START OFFICE REGISTER");
            OfficeModel savedOffice = officeService.officeRegister(token, officeModel);
            log.info("END OFFICE REGISTER");
            return new ResponseEntity<>(savedOffice, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAllOfficeDTO", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllOfficeDTO(
            @RequestHeader("Authorization") String token
    ) {
        log.info("START GET ALL OFFICE DTO");
        List<OfficeDetailsDTO> listOfficeDTO = officeService.getAllOfficesDTO(token);
        log.info("END GET ALL OFFICE DTO");
        return new ResponseEntity<>(listOfficeDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAllOffice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllOffice() {
        log.info("START GET ALL OFFICE");
        List<OfficeModel> officeModelList = officeService.getAllOffices();
        log.info("END GET ALL OFFICE");
        return new ResponseEntity<>(officeModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOfficeById/{officeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOfficeById(@PathVariable Long officeId) {
        try {
            log.info("START GET OFICE BY ID: ");
            Optional<OfficeModel> officeModel = Optional.ofNullable(officeService.getOfficeById(officeId)
                    .orElseThrow(() -> new RuntimeException("Consultorio no encontrado")));
            log.info("END GET OFICE BY ID: ");
            return new ResponseEntity<>(officeModel, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    @RequestMapping(value = "/getOfficesByTenantId/{tenantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOfficesByTenantId(@PathVariable Long tenantId) {
        log.info("START GET OFFICES BY TENANT ID: ");
        List<OfficeModel> officeModelList = officeService.getOfficesByTenantId(tenantId);
        log.info("END GET OFFICES BY TENANT ID: ");
        return new ResponseEntity<>(officeModelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateOfficeById/{officeId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOfficeById(
            @RequestHeader("Authorization") String token,
            @Valid @PathVariable Long officeId,
            @Valid @RequestBody OfficeDTO officeDTO) {
        try {
            log.info("START OFFICE UPDATE BY ID");
            OfficeModel officeModelEncontrado = officeService.updateOffice(token, officeId, officeDTO);
            log.info("END OFFICE UPDATE BY ID");
            return new ResponseEntity<>(officeModelEncontrado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    @RequestMapping(value = "deleteOfficeById/{officeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOfficeById(
            @Valid @PathVariable Long officeId
    ) {
        log.info("START OFFICE DELETE BY ID");
        OfficeModel officeModelEncontrado = officeService.deleteOfficeById(officeId);
        log.info("END OFFICE DELETE BY ID");
        return new ResponseEntity<>(officeModelEncontrado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "deleteOfficeByTenantId/{tenantId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOfficeByTenantId(
            @Valid @PathVariable Long tenantId
    ) {
        log.info("START DELETE OFFICES BY TENANT ID");
        officeService.deleteOfficeByTenantId(tenantId);
        log.info("END DELETE OFFICES BY TENANT ID");

        return new ResponseEntity<>(true, HttpStatus.OK);

    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


}
