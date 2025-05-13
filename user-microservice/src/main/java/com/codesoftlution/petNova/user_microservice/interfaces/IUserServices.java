package com.codesoftlution.petNova.user_microservice.interfaces;

import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.models.UserModel;

import java.util.List;

public interface IUserServices {
    List<UserDetailDTO> getAllUsers();
    UserModel createUser(UserModel userModel) ;
    UserModel updateUser(Long userId, UserDetailDTO dto);
    boolean deleteUserById(Long userId);

}
