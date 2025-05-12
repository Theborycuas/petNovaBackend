package com.codesoftlution.petNova.user_microservice.dtos;

import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetailDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String idNumber;
    private String phoneNumber;
    private String address;
    private String avatarUrl;
    private boolean active;
    private boolean emailVerified;
    private String preferredLanguage;
    private String timeZone;
    private Long officeId;
    private Long tenantId;
    private RoleModel role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
