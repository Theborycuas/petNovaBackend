package com.codesoftlution.petnova.tenantmicroservice.controllers;

import com.codesoftlution.petnova.tenantmicroservice.interfaces.ISubscriptionService;
import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("apiPetNova/subscriptions")
public class SubscriptionController {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private ISubscriptionService service;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createSubscription(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody SubscriptionModel subscription) {
        log.info("START CREATE SUBSCRIPTION");
        SubscriptionModel created = service.create(subscription);
        log.info("END CREATE SUBSCRIPTION");
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
        log.info("START GET ALL SUBSCRIPTIONS");
        List<SubscriptionModel> list = service.findAll();
        log.info("END GET ALL SUBSCRIPTIONS");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getByTenant/{tenantId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getByTenant(
            @RequestHeader("Authorization") String token,
            @PathVariable Long tenantId) {
        log.info("START GET SUBSCRIPTIONS BY TENANT");
        List<SubscriptionModel> list = service.findByTenantId(tenantId);
        log.info("END GET SUBSCRIPTIONS BY TENANT");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionModel subscription) {
        log.info("START UPDATE SUBSCRIPTION");
        SubscriptionModel updated = service.update(id, subscription);
        log.info("END UPDATE SUBSCRIPTION");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        log.info("START DELETE SUBSCRIPTION");
        service.delete(id);
        log.info("END DELETE SUBSCRIPTION");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
