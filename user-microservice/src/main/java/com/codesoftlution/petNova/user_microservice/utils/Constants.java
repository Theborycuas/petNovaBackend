package com.codesoftlution.petNova.user_microservice.utils;

import java.util.Date;

public class Constants {
    public final static String STANDARDCHARSETS_UFT_8 = "UTF-8";
    public final static String ALGORITHM_AES = "AES";
    public final static String TRANSFORMATION_AES = "AES/CBC/PKCS5PADDING";
    public final static String PN_INIT_VECTOR = "PetNovaencryptIV";
    public final static String PN_AES_KEY = "PetNovaEncryptKe";
    public final static int PN_SESION_TIME = 1000 * 60 * 30; // 30 Minutos
    //public final static int PN_SESION_TIME = 1000 * 60 * 60 * 24; // 24 horas

    public final static String AUTHORIZATION_HEADER = "PnAuthorization";
    public final static String PREFIX_BEARER = "Bearer ";
}
