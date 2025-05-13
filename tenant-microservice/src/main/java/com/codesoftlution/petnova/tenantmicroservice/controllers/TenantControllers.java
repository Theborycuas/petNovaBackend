package com.codesoftlution.petnova.tenantmicroservice.controllers;

import com.codesoftlution.petnova.tenantmicroservice.dtos.TenantDTO;
import com.codesoftlution.petnova.tenantmicroservice.models.TenantModel;
import com.codesoftlution.petnova.tenantmicroservice.servicesImpl.TenantServiceImpl;
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

@RestController
@RequestMapping("apiPetNova/tenants")
public class TenantControllers {

    @Autowired
    private TenantServiceImpl tenantServiceImpl;

    Logger log = Logger.getLogger(TenantControllers.class.getName());

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/createTenant", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createTenant(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TenantDTO tenantDTO
    ) {
        log.info("START CREATE TENANT");
        TenantModel savedTenant = tenantServiceImpl.createTenant(token, tenantDTO);
        log.info("END CREATE TENANT");
        return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAllTenants", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTenants() {
        log.info("START GET ALL TENANTS");
        List<TenantModel> allTenants = tenantServiceImpl.getAllTenants();
        log.info("END GET ALL TENANTS");
        return new ResponseEntity<>(allTenants, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    @RequestMapping(value = "/getTenantDetailById/{tenantId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTenantDetailById(
            @PathVariable Long tenantId
    ){
        log.info("START GET TENANT BY ID");
        Optional<TenantModel> tenantModel = Optional.ofNullable(tenantServiceImpl.getTenantById(tenantId))
                .orElseThrow(() -> new RuntimeException("Tenant no Encontrado"));
        log.info("END GET TENANT BY ID");
        return new ResponseEntity<>(tenantModel, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    @RequestMapping(value = "/getBasicTenantById/{tenantId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getBasicTenantById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long tenantId
    ){
        log.info("START GET TENANT BY ID");
        TenantDTO tenantDTO = tenantServiceImpl.getBasicTenantById(token, tenantId);
        log.info("END GET TENANT BY ID");
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    @RequestMapping(value = "/updateTenantById/{tenantId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateTenantById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long tenantId,
            @Valid @RequestBody TenantDTO updateTenantDTO
    ){
        log.info("START GET TENANT BY ID");
        TenantModel updatedTenant = tenantServiceImpl.updateTenantById(token, tenantId, updateTenantDTO);
        log.info("END GET TENANT BY ID");
        return new ResponseEntity<>(updatedTenant, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @RequestMapping(value = "/deleteTenantById/{tenantId}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteTenantById(
            @RequestHeader("Authorization")  String token,
            @Valid @PathVariable Long tenantId
    ){
        log.info("START DELETE TENANT BY ID");
        boolean tenantDeleted = tenantServiceImpl.deleteTenantById(token, tenantId);
        log.info("END DELETE TENANT BY ID");
        return new ResponseEntity<>(tenantDeleted, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOk", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getOk() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
