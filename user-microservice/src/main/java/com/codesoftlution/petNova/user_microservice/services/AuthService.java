package com.codesoftlution.petNova.user_microservice.services;

import com.codesoftlution.petNova.user_microservice.clientsfeign.OfficeFeignClient;
import com.codesoftlution.petNova.user_microservice.dtos.AuthRequest;
import com.codesoftlution.petNova.user_microservice.dtos.AuthResponse;
import com.codesoftlution.petNova.user_microservice.dtos.OfficeDTO;
import com.codesoftlution.petNova.user_microservice.dtos.RegisterRequest;
import com.codesoftlution.petNova.user_microservice.models.RoleModel;
import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.respositories.IRoleRepository;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    OfficeFeignClient officeFeignClient;

    public AuthResponse register(RegisterRequest request) {
        Long officeIdFound = null;

        if(request.getRole() == null){
            RoleModel role = new RoleModel();
            role.setId(4L);
            request.setRole(role);
        }
        RoleModel role = roleRepository.findById(request.getRole().getId())
                .orElseThrow(() -> new RuntimeException("ROL NO ENCONTRADO"));

        if ("VETERINARIO".equalsIgnoreCase(role.getRoleName())) {
            if (request.getOfficeId() != null) {
                OfficeDTO officeDTO = officeFeignClient.getOfficeById(request.getOfficeId());
                if (officeDTO != null) {
                    officeIdFound = officeDTO.getId();
                }
            }
        }

        var user = UserModel.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(role)
                .idNumber(request.getIdNumber())
                .phoneNumber(request.getPhoneNumber())
                .officeId(officeIdFound)
                .creationDate(LocalDateTime.now())
                .active(false)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "USUARIO NO ENCONTRADO"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean validateUserTokenActive(String token) {
        String username = jwtService.extractUsername(token);
        var userFound = userRepository.findByUsernameAndActive(username, true)
                .orElseThrow(() -> new RuntimeException("USUARIO NO ENCONTRADO"));
        return jwtService.isTokenValid(token, userFound);

    }
}
