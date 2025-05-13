package com.codesoftlution.petnova.tenantmicroservice.clientsfeign;

import com.codesoftlution.petnova.tenantmicroservice.dtos.UserPublicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @PutMapping("/users/updateUserTenantManage/{userId}")
    boolean updateUserTenantManage(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long userId,
            @RequestBody Long tenantId
    );

    @GetMapping("users/getUserByTenantId/{tenantId}")
    UserPublicDTO getUserByTenantId(
            @RequestHeader("Authorization") String token,
            @PathVariable("tenantId") Long tenantId
    );

}
