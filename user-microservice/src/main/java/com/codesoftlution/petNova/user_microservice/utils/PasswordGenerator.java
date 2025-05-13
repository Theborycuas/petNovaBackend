package com.codesoftlution.petNova.user_microservice.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generatePassword(String name, String phone, String email) {
        // Extraer partes relevantes
        String namePart = name != null ? name.replaceAll("\\s+", "").substring(0, Math.min(3, name.length())).toLowerCase() : "usr";
        String phonePart = phone != null && phone.length() >= 4 ? phone.substring(phone.length() - 4) : "0000";
        String emailPart = email != null ? email.split("@")[0] : "mail";

        // Generar parte aleatoria segura (4 bytes codificados en Base64)
        byte[] randomBytes = new byte[4];
        secureRandom.nextBytes(randomBytes);
        String randomSuffix = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        // Armar contraseña (ejemplo: jos1234borys_x8Az)
        return namePart + phonePart + emailPart.substring(0, Math.min(4, emailPart.length())) + "_" + randomSuffix;
    }
}
