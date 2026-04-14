package comp_T08;/*
UNAM -FES ARAGON
Ingenieria en Computacion
Coompiladores

-----------Programa 6 -----------------------------------
Codigo Version 2 AFD para notacion cientifica

*/

import javax.swing.JOptionPane;

public class lenguajeMike_AFD2 {

    private int indice = 0;
    private String cadena = "";

    private final int error = -1;
    private final int aceptado = 1;

    public static void main(String[] args) {
        lenguajeMike_AFD2 app = new lenguajeMike_AFD2();

        app.cadena = JOptionPane.showInputDialog("Dame la cadena");
        if (app.cadena == null) app.cadena = ""; // por si cancelan

        int valor = app.estado_q0();

        if (valor == app.aceptado) {
            JOptionPane.showMessageDialog(null, "Cadena Válida");
        } else {
            JOptionPane.showMessageDialog(null, "Cadena Inválida");
        }
    }

    private char siguienteCaracter() {
        char caracter = '#'; // fin de cadena

        if (indice < cadena.length()) {
            caracter = cadena.charAt(indice);
            indice++;
        }

        return caracter;
    }

    private int estado_q0() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_q1();
        }
        if (Character.isLetter(c)) {
            return estado_q2();
        }
        switch (c) {
            case '(':
                return estado_q11();
            case ')':
                return estado_q12();
            case ':':
                return estado_q9();
            case ';':
                return estado_q8();
            default:
                return error;
        }
    }

    private int estado_q1() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_q1();
        }
        if (Character.isLetter(c)) {
            return estado_q1();
        }
        return estado_q3();
    }

    private int estado_q2() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_q2();
        }

        switch (c) {
            default:
                return estado_q4();
        }
    }

    private int estado_q3() {
        char c = siguienteCaracter();

        switch (c) {
            case '#':
                return aceptado;
            case '.':
                return estado_q5();
            default:
                return error;
        }
    }

    private int estado_q4() {
        char c = siguienteCaracter();

        switch (c) {
            case '#':
                return aceptado;
            default:
                return error;
        }
    }

    private int estado_q5() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_q6();
        }
        return error;
    }

    private int estado_q6() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_q7();
        }
        return estado_q7();
    }

    private int estado_q7() {
        char c = siguienteCaracter();
        switch (c) {
            default:
                return aceptado;
        }
    }

    private int estado_q8() {
        char c = siguienteCaracter();
        switch (c) {
            default:
                return aceptado;
        }
    }

    private int estado_q9() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_q7();
        }
        switch (c) {
            case '=':
                return estado_q10();
            default:
                return error;
        }
    }

    private int estado_q10() {
        char c = siguienteCaracter();
        switch (c) {
            default:
                return aceptado;
        }
    }


    private int estado_q11() {
        char c = siguienteCaracter();
        switch (c) {
            default:
                return aceptado;
        }

    }

    private int estado_q12() {
        char c = siguienteCaracter();
        switch (c) {
            default:
                return aceptado;
        }

    }
}