package com.codesoftlution.petNova.user_microservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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
    private String avatarUrl;
    private Long officeId;
}
