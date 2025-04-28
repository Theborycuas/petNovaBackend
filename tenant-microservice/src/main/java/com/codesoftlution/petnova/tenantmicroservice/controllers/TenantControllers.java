package com.codesoftlution.petnova.tenantmicroservice.controllers;

import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.services.TenantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("apiPetNova/tenants")
public class TenantControllers {

    @Autowired
    private TenantService tenantService;

    Logger log = Logger.getLogger(TenantControllers.class.getName());

    @RequestMapping(value = "/createTenant", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createTenant(
            @Valid @RequestBody TenantModel tenantModel
            ){
        try {
            log.info("START CREATE TENANT");
            tenantService.createTenant(tenantModel);
            log.info("END CREATE TENANT");
            return new ResponseEntity<>(tenantModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
