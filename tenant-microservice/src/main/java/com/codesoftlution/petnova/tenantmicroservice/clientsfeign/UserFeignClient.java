package com.codesoftlution.petnova.tenantmicroservice.clientsfeign;

import com.codesoftlution.petnova.tenantmicroservice.dtos.UserPublicDTO;
import com.codesoftlution.petnova.tenantmicroservice.request.RequestUpdateTenantManager;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @PutMapping("/users/updateUserTenantManage")
    boolean updateUserTenantManage(
            @RequestHeader("Authorization") String token,
            @RequestBody RequestUpdateTenantManager requestUpdateTenantManager
            );

    @GetMapping("users/getUsersByTenantId/{tenantId}")
    List<UserPublicDTO> getUsersByTenantId(
            @RequestHeader("Authorization") String token,
            @PathVariable("tenantId") Long tenantId
    );

}
