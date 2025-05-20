package com.codesoftlution.petNova.user_microservice.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestUpdateTenantManager {
    private Long tenantId;
    private boolean isDeleted;
    private List<Long> managerIds;
}
