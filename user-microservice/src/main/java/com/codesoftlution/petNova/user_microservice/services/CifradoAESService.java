package com.codesoftlution.petNova.user_microservice.services;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.codesoftlution.petNova.user_microservice.utils.Constants.*;
import static com.codesoftlution.petNova.user_microservice.utils.Constants.TRANSFORMATION_AES;

public class CifradoAESService {

    /* public static String descifrar(String cifrado) {
         try {
             IvParameterSpec iv = new IvParameterSpec(cifrado.getBytes());
         }
     }*/
    public static String pnCifradoService(String stringDescifrado) {
        try {
            IvParameterSpec iv = new IvParameterSpec(PN_INIT_VECTOR.getBytes(STANDARDCHARSETS_UFT_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(PN_AES_KEY.getBytes(STANDARDCHARSETS_UFT_8), ALGORITHM_AES);
            Cipher cipher;
            cipher = Cipher.getInstance(TRANSFORMATION_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(stringDescifrado.getBytes());
            return Base64.getUrlEncoder()
                    .encodeToString(encrypted);
        } catch (UnsupportedEncodingException | NoSuchPaddingException | NoSuchAlgorithmException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String pnDescifradoService(String stringCifrado) {
        try {
            IvParameterSpec iv = new IvParameterSpec(PN_INIT_VECTOR.getBytes(STANDARDCHARSETS_UFT_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(PN_AES_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] desencriptado = cipher.doFinal(Base64.getUrlDecoder().decode(stringCifrado));
            return new String(desencriptado);
        }catch (InvalidAlgorithmParameterException | UnsupportedEncodingException | NoSuchPaddingException |
                IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
