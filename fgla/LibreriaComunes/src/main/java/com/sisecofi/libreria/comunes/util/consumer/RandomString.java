package com.sisecofi.libreria.comunes.util.consumer;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomString {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CARACTERES_ESPECIALES = "!@#$%^&*()-_=+<>?";

	public static String generarRandomString(int numeroCaracteres) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(numeroCaracteres);

        int specialCharIndex = random.nextInt(CARACTERES_ESPECIALES.length());
        stringBuilder.append(CARACTERES_ESPECIALES.charAt(specialCharIndex));

        for (int i = 1; i < numeroCaracteres; i++) {
            int index = random.nextInt(CARACTERES.length());
            stringBuilder.append(CARACTERES.charAt(index));
        }

        return combinarString(stringBuilder.toString(), random);
    }

    private static String combinarString(String string, SecureRandom random) {
        char[] caracteres = string.toCharArray();
        for (int i = caracteres.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            char temp = caracteres[index];
            caracteres[index] = caracteres[i];
            caracteres[i] = temp;
        }
        return new String(caracteres);
    }
}
