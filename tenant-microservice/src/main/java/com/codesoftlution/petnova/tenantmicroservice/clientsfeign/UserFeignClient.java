package com.codesoftlution.petnova.tenantmicroservice.clientsfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @PutMapping("/users/updateUserTenantManage/{userId}")
    boolean updateUserTenantManage(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long userId,
            @RequestBody Long tenantId
    );
}
