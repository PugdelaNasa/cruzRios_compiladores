/*
UNAM -FES ARAGON
Ingenieria en Computacion
Coompiladores

-----------Programa 6 -----------------------------------
Codigo Version 2 AFD para notacion cientifica

*/
package comp_T06;

import javax.swing.JOptionPane;

public class notCientifica_AFD2 {

    private int indice = 0;
    private String cadena = "";

    private final int error = -1;
    private final int aceptado = 1;

    public static void main(String[] args) {
        notCientifica_AFD2 app = new notCientifica_AFD2();

        app.cadena = JOptionPane.showInputDialog("Dame la cadena");
        if (app.cadena == null) app.cadena = ""; // por si cancelan

        int valor = app.estado_1();

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

    private int estado_1() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_2();
        }
        return error;
    }

    private int estado_2() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_2();
        }

        switch (c) {
            case 'e':
                return estado_5();
            case '.':
                return estado_3();
            default:
                return error;
        }
    }

    private int estado_3() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_4();
        }

        return error;

    }

    private int estado_4() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_4();
        }

        switch (c) {
            case 'e':
                return estado_5();
            case '#':
                return aceptado;
            default:
                return error;
        }
    }

    private int estado_5() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_7();
        }
        switch (c) {
            case '+':
                return estado_6();
            case '-':
                return estado_6();
            default:
                return error;
        }
    }

    private int estado_6() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_4();
        }
        return error;
    }

    private int estado_7() {
        char c = siguienteCaracter();
        if (Character.isDigit(c)) {
            return estado_7();
        }
        switch (c) {
            case '#':
                return aceptado;
            default:
                return error;
        }
    }


}