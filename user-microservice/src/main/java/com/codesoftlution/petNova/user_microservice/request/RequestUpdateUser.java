package com.codesoftlution.petNova.user_microservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestUpdateUser {


    private String name;
    private String idNumber;
    private String username;

    private String email;

    private String phoneNumber;
    private String avatarUrl;

}
