package com.codesoftlution.petNova.user_microservice.request;

import lombok.Data;

@Data
public class RequestUpdateTenantManager {
    private Long tenantId;
    private boolean isDeleted;
}
