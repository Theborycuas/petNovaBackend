package com.codesoftlution.petnova.tenantmicroservice.controllers;

import com.codesoftlution.petnova.tenantmicroservice.interfaces.ISubscriptionPlanService;
import com.codesoftlution.petnova.tenantmicroservice.models.SubscriptionPlanModel;
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
import java.util.Optional;

@RestController
@RequestMapping("apiPetNova/subscription-plans")
public class SubscriptionPlanController {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionPlanController.class);

    @Autowired
    private final ISubscriptionPlanService subscriptionPlanService;

    @Autowired
    public SubscriptionPlanController(ISubscriptionPlanService service) {
        this.subscriptionPlanService = service;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createPlan(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody SubscriptionPlanModel plan) {
        log.info("START CREATE SUBSCRIPTION PLAN");
        SubscriptionPlanModel created = subscriptionPlanService.create(plan);
        log.info("END CREATE SUBSCRIPTION PLAN");
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllPlans(@RequestHeader("Authorization") String token) {
        log.info("START GET ALL SUBSCRIPTION PLANS");
        List<SubscriptionPlanModel> plans = subscriptionPlanService.findAll();
        log.info("END GET ALL SUBSCRIPTION PLANS");
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getPlanById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        log.info("START GET SUBSCRIPTION PLAN BY ID");
        Optional<SubscriptionPlanModel> plan = subscriptionPlanService.findById(id);
        log.info("END GET SUBSCRIPTION PLAN BY ID");
        return plan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updatePlan(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionPlanModel plan) {
        log.info("START UPDATE SUBSCRIPTION PLAN");
        SubscriptionPlanModel updated = subscriptionPlanService.update(id, plan);
        log.info("END UPDATE SUBSCRIPTION PLAN");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deletePlan(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        log.info("START DELETE SUBSCRIPTION PLAN");
        subscriptionPlanService.delete(id);
        log.info("END DELETE SUBSCRIPTION PLAN");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
