package com.codesoftlution.petnova.tenantmicroservice.controllers;

import com.codesoftlution.petnova.tenantmicroservice.interfaces.IInvoiceService;
import com.codesoftlution.petnova.tenantmicroservice.models.InvoiceModel;
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
@RequestMapping("apiPetNova/invoices")
public class InvoiceController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private IInvoiceService service;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createInvoice(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody InvoiceModel invoice) {
        log.info("START CREATE INVOICE");
        InvoiceModel created = service.create(invoice);
        log.info("END CREATE INVOICE");
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllInvoices(@RequestHeader("Authorization") String token) {
        log.info("START GET ALL INVOICES");
        List<InvoiceModel> list = service.findAll();
        log.info("END GET ALL INVOICES");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getByTenant/{tenantId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getByTenant(@RequestHeader("Authorization") String token, @PathVariable Long tenantId) {
        log.info("START GET INVOICES BY TENANT");
        List<InvoiceModel> list = service.findByTenantId(tenantId);
        log.info("END GET INVOICES BY TENANT");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getBySubscription/{subscriptionId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getBySubscription(@RequestHeader("Authorization") String token, @PathVariable Long subscriptionId) {
        log.info("START GET INVOICES BY SUBSCRIPTION");
        List<InvoiceModel> list = service.findBySubscriptionId(subscriptionId);
        log.info("END GET INVOICES BY SUBSCRIPTION");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateInvoice(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @Valid @RequestBody InvoiceModel invoice) {
        log.info("START UPDATE INVOICE");
        InvoiceModel updated = service.update(id, invoice);
        log.info("END UPDATE INVOICE");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteInvoice(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        log.info("START DELETE INVOICE");
        service.delete(id);
        log.info("END DELETE INVOICE");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
