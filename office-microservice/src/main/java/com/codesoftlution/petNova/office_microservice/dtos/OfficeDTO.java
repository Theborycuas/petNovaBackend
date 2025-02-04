package com.codesoftlution.petNova.office_microservice.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDTO {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String linkLogoPhoto;
    private boolean active;
    private Long veterinarioId;
}
