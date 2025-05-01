package com.codesoftlution.petnova.tenantmicroservice.controllers;

import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.services.TenantService;
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
@RequestMapping("apiPetNova/tenants")
public class TenantControllers {

    @Autowired
    private TenantService tenantService;

    Logger log = Logger.getLogger(TenantControllers.class.getName());

    @RequestMapping(value = "/createTenant", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createTenant(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TenantModel tenantModel
    ) {
        log.info("START CREATE TENANT");
        TenantModel savedTenant = tenantService.createTenant(tenantModel);
        log.info("END CREATE TENANT");
        return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getAllTenants", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTenants() {
        log.info("START GET ALL TENANTS");
        List<TenantModel> allTenants = tenantService.getAllTenants();
        log.info("END GET ALL TENANTS");
        return new ResponseEntity<>(allTenants, HttpStatus.OK);
    }

    @RequestMapping(value = "/getTenantById/{tenantId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTenantById(
            @PathVariable Long tenantId
    ){
        log.info("START GET TENANT BY ID");
        Optional<TenantModel> tenantModel = Optional.ofNullable(tenantService.getTenantById(tenantId))
                .orElseThrow(() -> new RuntimeException("Tenant no Encontrado"));
        log.info("END GET TENANT BY ID");
        return new ResponseEntity<>(tenantModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
