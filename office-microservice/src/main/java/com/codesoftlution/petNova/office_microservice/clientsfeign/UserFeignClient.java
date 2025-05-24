package com.codesoftlution.petNova.office_microservice.clientsfeign;

import com.codesoftlution.petNova.office_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.office_microservice.request.RequestUpdateOfficeManager;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @GetMapping("users/getUsersByOfficeId/{officeId}")
    List<UserPublicDTO> getUsersByOfficeId(
            @RequestHeader("Authorization") String token,
            @PathVariable("officeId") Long officeId
    );

    @PutMapping("/users/updateUserOfficeManage")
    boolean updateUserOfficeManage(
            @RequestHeader("Authorization") String token,
            @RequestBody RequestUpdateOfficeManager requestUpdateOfficeManager
    );

}
