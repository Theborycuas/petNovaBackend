package com.codesoftlution.petNova.user_microservice.config;

import com.codesoftlution.petNova.user_microservice.models.UserModel;
import com.codesoftlution.petNova.user_microservice.respositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Busca al usuario en la base de datos
        UserModel userFound = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EL USUARIO NO EXISTE"));

        // Validar que este activo o verificado el email
        if(!userFound.isActive()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "EL CUENTA NO ACTIVADA");
        }

        if(!userFound.isEmailVerified()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CORREO NO VERIFICADO");
        }

        // Verifica la contraseña
        if (!passwordEncoder.matches(password, userFound.getPassword())) {
            throw new BadCredentialsException("CONTRASEÑA INCORRECTA");
        }

        // Devuelve un objeto de autenticación
        return new UsernamePasswordAuthenticationToken(
                userFound,
                password,
                userFound.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
