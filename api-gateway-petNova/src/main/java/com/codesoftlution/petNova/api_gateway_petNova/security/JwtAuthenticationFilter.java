package com.codesoftlution.petNova.api_gateway_petNova.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.codesoftlution.petNova.api_gateway_petNova.utils.Constants.PREFIX_BEARER;

public class JwtAuthenticationFilter implements WebFilter {

    private final String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    public JwtAuthenticationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = extractToken(exchange.getRequest());

        if (token != null && validateToken(token)) {
            Authentication auth = createAuthentication(token);
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        }

        return chain.filter(exchange);
    }

    /**
     * Valida un token JWT
     * @param token Token a validar
     * @return true si es válido, false si no
     */
    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // Firma inválida
        } catch (MalformedJwtException ex) {
            // Token malformado
        } catch (ExpiredJwtException ex) {
            // Token expirado
        } catch (UnsupportedJwtException ex) {
            // Token no soportado
        } catch (IllegalArgumentException ex) {
            // Claims vacíos
        }
        return false;
    }

    private String extractToken(ServerHttpRequest request) {
        // 1. Obtener el encabezado Authorization
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 2. Verificar que sea un Bearer Token válido
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(PREFIX_BEARER)) {
            return authHeader.substring(7); // Extraer el token después de "Bearer "
        }

        // 3. Alternativa: Buscar en parámetros de query (opcional)
        String tokenParam = request.getQueryParams().getFirst("token");
        if (StringUtils.hasText(tokenParam)) {
            return tokenParam;
        }

        return null; // No se encontró token
    }


    private Authentication createAuthentication(String token) {
        // 1. Parsear el token JWT
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 2. Extraer información del usuario
        String username = claims.getSubject();
        String roles = claims.get("roles", String.class);

        // 3. Crear UserDetails (puedes ajustar según tu estructura de roles)
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("") // No necesitamos la contraseña después de autenticado
                .authorities(roles.split(","))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        // 4. Crear objeto Authentication
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    /**
     * Genera un token JWT
     * @param authentication Objeto de autenticación de Spring Security
     * @return Token JWT
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Generar la clave HMAC
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Obtiene el username del token JWT
     * @param token Token JWT
     * @return Nombre de usuario
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Obtiene los roles del token JWT
     * @param token Token JWT
     * @return Lista de roles
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (List<String>) claims.get("roles");
    }
}
