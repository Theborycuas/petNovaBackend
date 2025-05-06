package com.codesoftlution.petNova.user_microservice.mappers;

import com.codesoftlution.petNova.user_microservice.dtos.UserDTO;
import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {

    public static UserDTO toUserDTO(final UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userModel.getId());
        userDTO.setName(userModel.getName());
        userDTO.setUsername(userModel.getUsername());
        userDTO.setEmail(userModel.getEmail());
        userDTO.setPassword(userModel.getPassword());
        userDTO.setRollName(userModel.getRole().getRoleName());
        userDTO.setPhoneNumber(String.valueOf(userModel.getPhoneNumber()));
        userDTO.setActive(userModel.isActive());
        userDTO.setEmailVerified(userModel.isEmailVerified());
        userDTO.setCreationDate(userModel.getCreatedAt());
        userDTO.setUpdateDate(userModel.getUpdateDate());
        userDTO.setLinkPerfilPhoto(userModel.getLinkPerfilPhoto());
        userDTO.setOfficeId(userModel.getOfficeId());
        return userDTO;
    }
    public static UserModel toUserModel(final UserDTO userDTO) {
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
        userModel.setUpdateDate(userDTO.getUpdateDate());
        userModel.setLinkPerfilPhoto(userDTO.getLinkPerfilPhoto());
        userModel.setOfficeId(userDTO.getOfficeId());
        return userModel;
    }

}
