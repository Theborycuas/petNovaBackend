package com.codesoftlution.petNova.user_microservice.mappers;

import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.user_microservice.models.UserModel;

import java.util.List;

public class UserMapper {

    public static UserDetailDTO toUserDetailDTO(
            final UserModel userModel,
            final List<Long> tenantIds,
            final List<Long> officeIds) {

        UserDetailDTO userDTO = new UserDetailDTO();
        userDTO.setId(userModel.getId());
        userDTO.setName(userModel.getName());
        userDTO.setUsername(userModel.getUsername());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setIdNumber(userModel.getIdNumber());
        userDTO.setRole(userModel.getRole());
        userDTO.setPhoneNumber(String.valueOf(userModel.getPhoneNumber()));
        userDTO.setCity(userModel.getCity());
        userDTO.setAddress(userModel.getAddress());
        userDTO.setActive(userModel.isActive());
        userDTO.setEmailVerified(userModel.isEmailVerified());
        userDTO.setCreatedAt(userModel.getCreatedAt());
        userDTO.setUpdateAt(userModel.getUpdateAt());
        userDTO.setAvatarUrl(userModel.getAvatarUrl());
        userDTO.setTenantIds(tenantIds);
        userDTO.setOfficeIds(officeIds);
        return userDTO;
    }

    public static UserPublicDTO toUserPublicDTO(final UserModel userModel) {
        UserPublicDTO userDTO = new UserPublicDTO();
        userDTO.setId(userModel.getId());
        userDTO.setName(userModel.getName());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setAvatarUrl(userModel.getAvatarUrl());
        return userDTO;
    }

   /* public static UserModel toUserModel(final UserDTO userDTO) {
        UserModel userModel = new UserModel();
        userModel.setId(userDTO.getId());
        userModel.setName(userDTO.getName());
        userModel.setUsername(userDTO.getUsername());
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(userDTO.getPassword());
        userModel.setPhoneNumber(String.valueOf(userDTO.getPhoneNumber()));
        userModel.setActive(userDTO.isActive());
        userModel.setEmailVerified(userDTO.isEmailVerified());
        userModel.setCreatedAt(userDTO.getCreationDate());
        userModel.setUpdateAt(userDTO.getUpdateDate());
        userModel.setAvatarUrl(userDTO.getAvatarUrl());
        userModel.setOfficeId(userDTO.getOfficeId());
        return userModel;
    }*/

}
