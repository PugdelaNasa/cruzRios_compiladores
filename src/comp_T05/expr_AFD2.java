/*
UNAM -FES ARAGON
Ingenieria en Computacion
Coompiladores

-----------Programa 5 -----------------------------------
Codigo Version 2 AFD para expresión regular (0*1 | 1*)01

*/package comp_T05;

import javax.swing.JOptionPane;

public class expr_AFD2 {

    private int indice = 0;
    private String cadena = "";

    private final int error = -1;
    private final int aceptado = 1;

    public static void main(String[] args) {
        expr_AFD2 app = new expr_AFD2();

        app.cadena = JOptionPane.showInputDialog("Dame la cadena");
        if (app.cadena == null) app.cadena = ""; // por si cancelan

        int valor = app.estado_A();

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

    private int estado_A() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_B();
            case '1':
                return estado_C();
            default:
                return error;
        }
    }

    private int estado_B() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_D();
            case '1':
                return estado_E();
            default:
                return error;
        }
    }

    private int estado_C() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_F();
            case '1':
                return estado_G();
            default:
                return error;
        }
    }

    private int estado_D() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_D();
            case '1':
                return estado_H();
            default:
                return error;
        }
    }

    private int estado_E() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_H();
            case '1':
                return estado_I();
            default:
                return error;
        }
    }

    private int estado_F() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_I();
            case '1':
                return estado_J();
            default:
                return error;
        }
    }

    private int estado_G() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_F();
            case '1':
                return estado_G();
            default:
                return error;
        }
    }

    private int estado_H() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_F();
            case '1':
                return estado_J();
            default:
                return error;
        }
    }

    private int estado_I() {
        char c = siguienteCaracter();

        switch (c) {
            default:
                return error;
        }
    }


    private int estado_J() {
        char c = siguienteCaracter();

        switch (c) {
            case '0':
                return estado_I();
            case '1':
                return estado_I();
            case '#':
                return aceptado; // fin de cadena en estado de aceptación
            default:
                return error;
        }
    }
}