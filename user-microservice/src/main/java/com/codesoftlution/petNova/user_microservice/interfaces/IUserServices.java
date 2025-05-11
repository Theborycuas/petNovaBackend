package com.codesoftlution.petNova.user_microservice.interfaces;

import com.codesoftlution.petNova.user_microservice.models.UserModel;

import java.util.List;

public interface IUserServices {
    public List<UserModel> getAllUsers();
    public UserModel createUser(UserModel userModel) ;

}
