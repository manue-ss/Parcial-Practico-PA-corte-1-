package com.alquiler.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

/**
 * Utilidad para cifrados y herramientas de seguridad.
 */
public class SecurityUtil {

    /**
     * Genera un hash SHA-256 puro a partir de una cadena.
     *
     * @param texto El texto plano a procesar.
     * @return El hash en formato hexadecimal (64 caracteres).
     */
    public static String encriptarSHA256(String texto) {
        try {
            // 1. Obtener la instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 2. Convertir el texto a bytes y generar el hash
            byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));

            // 3. Convertir bytes a String Hexadecimal usando HexFormat (Java 17+)
            return HexFormat.of().formatHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            // Error teórico
            throw new RuntimeException("Error: No se encontró el algoritmo SHA-256", e);
        }
    }
}
