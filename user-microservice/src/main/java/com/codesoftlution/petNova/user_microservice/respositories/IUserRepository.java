package com.codesoftlution.petNova.user_microservice.respositories;


import com.codesoftlution.petNova.user_microservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsernameAndActive(String email, boolean active);
    UserModel findByTokenAndActive(String token, boolean active);
}
