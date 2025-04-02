package com.codesoftlution.petNova.api_gateway_petNova.utils;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class SecurityUtils {

    /**
     * Obtiene el nombre de usuario autenticado actualmente
     * @return Mono<String> con el username o Mono.empty() si no autenticado
     */
    public static Mono<String> getCurrentUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth == null || !auth.isAuthenticated()) {
                        return Mono.empty();
                    }
                    return Mono.just(auth.getName());
                });
    }

    /**
     * Obtiene los roles del usuario actual
     * @return Mono<String[]> con los roles o array vacío si no autenticado
     */
    public static Mono<String[]> getCurrentUserRoles() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth == null || !auth.isAuthenticated()) {
                        return Mono.just(new String[]{});
                    }
                    return Mono.just(auth.getAuthorities().stream()
                            .map(Object::toString)
                            .toArray(String[]::new));
                });
    }

    /**
     * Verifica si el usuario actual tiene un rol específico
     * @param role Rol a verificar (ej: "ADMIN")
     * @return Mono<Boolean> con el resultado
     */
    public static Mono<Boolean> hasCurrentUserRole(String role) {
        return getCurrentUserRoles()
                .map(roles -> {
                    for (String r : roles) {
                        if (r.contains(role)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    /**
     * Obtiene los detalles completos del usuario autenticado
     * @return Mono<UserDetails> con los detalles o Mono.empty()
     */
    public static Mono<UserDetails> getCurrentUserDetails() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth == null || !auth.isAuthenticated()) {
                        return Mono.empty();
                    }
                    Object principal = auth.getPrincipal();
                    if (principal instanceof UserDetails) {
                        return Mono.just((UserDetails) principal);
                    }
                    return Mono.empty();
                });
    }

    /**
     * Extrae el token JWT del encabezado Authorization
     * @param authHeader Valor del encabezado "Authorization"
     * @return Token JWT o null si no es válido
     */
    public static String extractJwtToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * Valida la estructura básica de un token JWT
     * @param token Token a validar
     * @return true si tiene estructura válida
     */
    public static boolean isJwtTokenValidStructure(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        String[] parts = token.split("\\.");
        return parts.length == 3; // Header.Payload.Signature
    }
}