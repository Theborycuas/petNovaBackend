package com.codesoftlution.petNova.user_microservice.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestUpdateOfficeManager {
    private Long OfficeId;
    private boolean isDeleted;
    private List<Long> managerIds;
}
