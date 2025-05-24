package com.codesoftlution.petNova.user_microservice.interfaces;

import com.codesoftlution.petNova.user_microservice.dtos.UserDetailDTO;
import com.codesoftlution.petNova.user_microservice.dtos.UserPublicDTO;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateOfficeManager;
import com.codesoftlution.petNova.user_microservice.request.RequestUpdateTenantManager;

import java.util.List;
import java.util.Optional;

public interface IUserServices {
    List<UserDetailDTO> getAllUsers();
    List<UserDetailDTO> getEligibleUsersForAssignment(String context);
    UserModel createUser(UserModel userModel) ;
    UserDetailDTO updateUser(Long userId, UserDetailDTO dto);
    boolean deleteUserById(Long userId);
    List<UserModel> updateUserTenantManage(RequestUpdateTenantManager requestUpdateTenantManager);
    List<UserModel> updateUserOfficeManage(RequestUpdateOfficeManager requestUpdateOfficeManager);
    List<UserPublicDTO> getUsersByTenantId(Long tenantId);
    List<UserPublicDTO> getUsersByOfficeId(Long officeId);

}
