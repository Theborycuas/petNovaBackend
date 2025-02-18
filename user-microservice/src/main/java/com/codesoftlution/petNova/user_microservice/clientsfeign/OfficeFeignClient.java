package com.codesoftlution.petNova.user_microservice.clientsfeign;

import com.codesoftlution.petNova.user_microservice.dtos.OfficeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "office-microservice", path = "/apiPetNova")
public interface OfficeFeignClient {

    @GetMapping("/offices/getOfficeById/{officeId}")
    OfficeDTO getOfficeById(@PathVariable("officeId") Long officeId);

}
