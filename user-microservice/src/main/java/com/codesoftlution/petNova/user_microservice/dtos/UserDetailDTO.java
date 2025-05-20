package com.codesoftlution.petNova.user_microservice.dtos;

import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDetailDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String idNumber;
    private String phoneNumber;
    private String city;
    private String address;
    private String avatarUrl;
    private boolean active;
    private boolean emailVerified;
    private String preferredLanguage;
    private String timeZone;
    private List<Long> tenantIds;
    private List<Long> officeIds;
    private RoleModel role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
