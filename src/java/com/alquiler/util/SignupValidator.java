package com.alquiler.util;

import com.alquiler.model.dto.ClienteDTO;
import com.alquiler.model.repository.ClienteRepository;

/**
 * Validador para el registro de usuarios (signup). Verifica calidad de datos y
 * unicidad.
 *
 * @author Acer-Pc
 */
public class SignupValidator {

    public static ValidationResult validar(ClienteDTO dto, ClienteRepository repo) {
        ValidationResult resultado;

        resultado = validarNombreCompleto(dto.getNombreCompleto());
        if (!resultado.isValido()) {
            return resultado;
        }

        resultado = validarNombreUsuario(dto.getNombreUsuario());
        if (!resultado.isValido()) {
            return resultado;
        }

        resultado = validarCorreo(dto.getCorreo());
        if (!resultado.isValido()) {
            return resultado;
        }

        resultado = validarTelefono(dto.getTelefono());
        if (!resultado.isValido()) {
            return resultado;
        }

        resultado = validarContrasenia(dto.getContrasenia());
        if (!resultado.isValido()) {
            return resultado;
        }

        // Verificar unicidad
        if (repo.existeUsername(dto.getNombreUsuario())) {
            return ValidationResult.error("nombreUsuario", "El nombre de usuario ya existe");
        }
        if (repo.existeCorreo(dto.getCorreo())) {
            return ValidationResult.error("correo", "El correo ya está registrado");
        }

        return ValidationResult.ok();
    }

    private static ValidationResult validarNombreCompleto(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return ValidationResult.error("nombreCompleto", "El nombre completo no puede estar vacío");
        }
        if (!nombre.trim().contains(" ")) {
            return ValidationResult.error("nombreCompleto", "Debe ingresar nombre y apellidos completos");
        }
        if (!nombre.matches("[A-Za-z ]+")) {
            return ValidationResult.error("nombreCompleto", "Solo se permiten letras y espacios para el nombre");
        }
        return ValidationResult.ok();
    }

    private static ValidationResult validarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            return ValidationResult.error("nombreUsuario", "El nombre de usuario es obligatorio");
        }
        if (nombreUsuario.length() < 3) {
            return ValidationResult.error("nombreUsuario", "El nombre de usuario debe tener al menos 3 caracteres");
        }
        if (!nombreUsuario.matches("[A-Za-z0-9_]+")) {
            return ValidationResult.error("nombreUsuario", "Solo se permiten letras, números y guión bajo");
        }
        return ValidationResult.ok();
    }

    private static ValidationResult validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            return ValidationResult.error("correo", "El correo es obligatorio");
        }
        if (!correo.contains("@") || correo.split("@").length != 2) {
            return ValidationResult.error("correo", "Debe ingresar un correo válido");
        }
        return ValidationResult.ok();
    }

    private static ValidationResult validarTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            return ValidationResult.error("telefono", "El teléfono es obligatorio");
        }
        if (!telefono.matches("[0-9]+")) {
            return ValidationResult.error("telefono", "Solo se permiten números en el teléfono");
        }
        if (!telefono.matches("3[0-9]{9}")) {
            return ValidationResult.error("telefono", "Debe ingresar número de telefono valido");
        }

        return ValidationResult.ok();
    }

    private static ValidationResult validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.isBlank()) {
            return ValidationResult.error("contrasenia", "La contraseña es obligatoria");
        }
        if (contrasenia.length() < 8) {
            return ValidationResult.error("contrasenia", "La contraseña debe tener al menos 8 caracteres");
        }
        if (!contrasenia.matches(".*[A-Z].*")) {
            return ValidationResult.error("contrasenia", "La contraseña debe contener al menos una letra mayúscula");
        }
        if (!contrasenia.matches(".*[a-z].*")) {
            return ValidationResult.error("contrasenia", "La contraseña debe contener al menos una letra minúscula");
        }
        if (!contrasenia.matches(".*[0-9].*")) {
            return ValidationResult.error("contrasenia", "La contraseña debe contener al menos un número");
        }

        if (!contrasenia.matches(".*[!@#$%^&*()].*")) {
            return ValidationResult.error("contrasenia", "La contraseña debe contener al menos un símbolo especial");
        }
        return ValidationResult.ok();
    }
}
