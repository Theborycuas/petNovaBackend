package com.codesoftlution.petNova.user_microservice.mappers;

import com.codesoftlution.petNova.user_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.user_microservice.models.UserModel;

public class UserMapper {

    public static UserDTO toUserDTO(final UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userModel.getId());
        userDTO.setName(userModel.getName());
        userDTO.setUsername(userModel.getUsername());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setRollName(userModel.getRole().getRoleName());
        userDTO.setPhoneNumber(String.valueOf(userModel.getPhoneNumber()));
        userDTO.setActive(userModel.isActive());
        userDTO.setEmailVerified(userModel.isEmailVerified());
        userDTO.setCreationDate(userModel.getCreationDate());
        userDTO.setUpdateDate(userModel.getUpdateDate());
        userDTO.setLinkPerfilPhoto(userModel.getLinkPerfilPhoto());
        userDTO.setOfficeId(userModel.getOfficeId());
        return userDTO;
    }
}
