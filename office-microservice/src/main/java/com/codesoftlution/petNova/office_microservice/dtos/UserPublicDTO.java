package com.codesoftlution.petNova.office_microservice.dtos;

import lombok.Data;

@Data
public class UserPublicDTO {
    private Long id;
    private String name;
    private String email;
    private String avatarUrl;
    private String roleName;
}