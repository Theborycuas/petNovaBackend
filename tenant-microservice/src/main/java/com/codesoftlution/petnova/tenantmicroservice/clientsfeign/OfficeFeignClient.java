package com.codesoftlution.petnova.tenantmicroservice.clientsfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "office-microservice", path = "/apiPetNova")
public interface OfficeFeignClient {

    @PostMapping("/offices/deleteOfficeByTenantId/{tenantId}")
    boolean deleteOfficeByTenantId(
            @RequestHeader("Authorization") String token,
            @PathVariable("tenantId") Long tenandId
            );

}
