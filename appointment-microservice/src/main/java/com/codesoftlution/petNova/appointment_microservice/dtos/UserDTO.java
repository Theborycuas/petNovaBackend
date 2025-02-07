package com.codesoftlution.petNova.appointment_microservice.dtos;

import jakarta.persistence.Column;
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
    private String rollName;
    private String phoneNumber;
    private boolean active;
    private String firebaseToken;
    private LocalDateTime creationDate;
    private String linkPerfilPhoto;
    @Column(nullable = false, unique = true)
    private Long officeId;
}
