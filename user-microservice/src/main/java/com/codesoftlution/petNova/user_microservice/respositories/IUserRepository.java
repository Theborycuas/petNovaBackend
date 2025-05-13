package com.codesoftlution.petNova.user_microservice.respositories;


import com.codesoftlution.petNova.user_microservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByUsernameAndActive(String username, boolean active);
    List<UserModel> findAllByDeletedAtIsNull();
}
