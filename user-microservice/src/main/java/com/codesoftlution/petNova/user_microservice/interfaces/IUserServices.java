package com.codesoftlution.petNova.user_microservice.interfaces;

import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateTenantManager;

import java.util.List;
import java.util.Optional;

public interface IUserServices {
    List<UserDetailDTO> getAllUsers();
    List<UserDetailDTO> getAllUsersNoTenantManager();
    UserModel createUser(UserModel userModel) ;
    UserModel updateUser(Long userId, UserDetailDTO dto);
    boolean deleteUserById(Long userId);
    UserModel updateUserTenantManage(Long userId, RequestUpdateTenantManager requestUpdateTenantManager);
    Optional<UserModel> getUserByTenantId(Long tenantId);

}
