package com.codesoftlution.petnova.tenantmicroservice.request;

import lombok.Data;

@Data
public class RequestUpdateTenantManager {
    private Long tenantId;
    private boolean isDeleted;
}
