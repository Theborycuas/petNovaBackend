package com.codesoftlution.petNova.office_microservice.clientsfeign;

import com.codesoftlution.petNova.office_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.office_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.office_microservice.response.AuthSesionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-microservice", path = "/apiPetNova")
public interface UserFeignClient {

    @GetMapping("users/getUserByOfficeId/{officeId}")
    UserPublicDTO getUserByOfficeId(
            @RequestHeader("Authorization") String token,
            @PathVariable("officeId") Long officeId
    );

    @PutMapping("/users/updateUserOfficeManage/{userId}")
    boolean updateUserOfficeManage(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long userId,
            @RequestBody Long officeId
    );

}
