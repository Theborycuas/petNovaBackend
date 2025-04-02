package com.codesoftlution.petNova.api_gateway_petNova.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String idNumber;
    private String username;
    private String email;
    private String password;
    private String rollName;
    private String phoneNumber;
    private boolean active;
    private boolean emailVerified;
    private String firebaseToken;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String linkPerfilPhoto;
    private Long officeId;
}
