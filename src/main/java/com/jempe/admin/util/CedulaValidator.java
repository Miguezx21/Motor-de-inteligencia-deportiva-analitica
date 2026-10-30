package com.jempe.admin.util;

import org.springframework.stereotype.Component;

/**
 * Validador de Cédula Ecuatoriana - Algoritmo Módulo 10
 *
 * VALIDACIÓN BACK-END del dato sensible requerido por la consigna.
 * Se invoca desde los servicios ANTES de persistir Atleta, Entrenador y PadreTutor.
 */
@Component
public class CedulaValidator {

    private static final int[] COEFICIENTES = {2, 1, 2, 1, 2, 1, 2, 1, 2};

    /**
     * Valida una cédula ecuatoriana usando el algoritmo Módulo 10.
     * @param cedula String de 10 dígitos
     * @return true si la cédula es válida
     */
    public boolean validar(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || (provincia > 24 && provincia != 30)) return false;

        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito > 5) return false;

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int resultado = Character.getNumericValue(cedula.charAt(i)) * COEFICIENTES[i];
            if (resultado >= 10) resultado -= 9;
            suma += resultado;
        }

        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int digitoCalculado = (suma % 10 == 0) ? 0 : (10 - (suma % 10));
        return digitoVerificador == digitoCalculado;
    }

    /**
     * Lanza excepción si la cédula es inválida.
     * Usado directamente desde los servicios.
     */
    public void validarOLanzar(String cedula) {
        if (!validar(cedula)) {
            throw new IllegalArgumentException(
                "La cédula '" + cedula + "' no es válida según el algoritmo Módulo 10. " +
                "Verifica los dígitos e intenta de nuevo."
            );
        }
    }
}
