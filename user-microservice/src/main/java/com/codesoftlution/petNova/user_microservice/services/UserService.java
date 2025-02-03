package com.codesoftlution.petNova.user_microservice.services;

import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    IUserRepository iUserRepository;

    public UserModel findUserByEmail(String email, boolean active) {
        return iUserRepository.findByUsernameAndActive(email, active)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserModel findUserByTokenAndActive(String token, boolean active) {
        return iUserRepository.findByTokenAndActive(token, active);
    }

    public List<UserModel> getUsers() {
        return iUserRepository.findAll();
    }

    public UserModel registerSetUser(UserModel userModel) {
        LocalDateTime dateNow = LocalDateTime.now();
        userModel.setCreationDate(dateNow);
        userModel.setToken("987654321");
        return iUserRepository.save(userModel);
    }



    public UserService(IUserRepository userRepository) {
        this.iUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe o no esta Activo"));
    }
}
