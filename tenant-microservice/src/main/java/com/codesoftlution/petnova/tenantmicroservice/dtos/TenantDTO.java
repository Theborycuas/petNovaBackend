package com.codesoftlution.petnova.tenantmicroservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TenantDTO {

    private String tenantName;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String city;
    private Long planId;
    private String currency;
    private List<Long> managerIds;

}
