package T03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class A {


    // Tabla
    // Columnas: entradas;,LETRA, DIGITO, FC
    // Filas: estados 0,1,2
    static int[][] tabla = {
            {0, 2, 1,0},  // Estado 0
            {1, 1, 1,0},  // Estado 1
            {2, 2, 2,1}   // Estado 2 (negación)
    };



    public static void main(String[] args) {

        String rutaArchivo = "C:\\Users\\janca\\OneDrive\\Documentos\\prueba.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;
            int numLinea = 1;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    System.out.println("Línea " + numLinea + ": (vacía) -> CADENA NO VALIDA");
                    numLinea++;
                    continue;
                }

                boolean valida = analizarCadena(linea);

                System.out.println("Línea " + numLinea + ": \"" + linea + "\" -> "
                        + (valida ? "CADENA ACEPTADA" : "CADENA NO VALIDA"));

                numLinea++;
            }

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + e.getMessage());
        }
    }

    //mi (repeat-until)
    static boolean analizarCadena(String cadena) {

        int estado = 0; // estado inicial
        int verificador = 0;
        int entrada;  //var aux
        int i = 0;

        do {
            char simbolo;

            // Leer siguiente símbolo; si se termina, enviamos FC '#'
            if (i < cadena.length()) {
                simbolo = cadena.charAt(i);
            } else {
                simbolo = '#'; // Fin cadena
            }
            i++;

            // Casos de entrada
            if (Character.isLetter(simbolo)) {
                entrada = 1;
            } else if (Character.isDigit(simbolo)) {
                entrada = 2;
            } else if (simbolo == '#' ) {
                entrada = 3;
            } else {
                // Símbolo inválido
                return false;
            }

            // Transición
            estado = tabla[estado][entrada];
            if (tabla[estado][3] == 0) return false;

        } while (tabla[estado][3] != 1); // UNTIL aceptar
        return true; // CADENA ACEPTADA
    }
}
