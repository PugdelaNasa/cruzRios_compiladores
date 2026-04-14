/*
-----------BETA Programa 5 -----------------------------------
Version 3 de la Tabla de Transciion el AFD1 00**00

*/
package comp_B04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class beta_AFD1_v3 {


    static int[][] tabla = {
            {0, 1, -1,0},  // Estado 0
            {1, 2, -1,0},  // Estado 1
            {2, 3, 2 ,0},   // Estado 2 (negación)
            {3, 4, 2 ,0},
            {4, 5, 2 ,1},
            {5, 5, -1,1},
    };



    public static void main(String[] args) {

        String rutaArchivo = "src/comp_B04/prueba00_00.txt";


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
        int entrada;  //var aux
        int i = 0;
        char simbolo;

        do {
            // Leer siguiente símbolo; si se termina, enviamos FC '#'
            if (i < cadena.length()) {
                simbolo = cadena.charAt(i);
            } else {
                simbolo = '#'; // Fin cadena
            }
            i++;

            // Casos de entrada
            if (simbolo == '0') {
                entrada = 1;
            } else if (simbolo == '1') {
                entrada = 2;
            } else if (simbolo == '#' ) {
                entrada = 0;
            } else {
                // Símbolo inválido
                return false;
            }
            System.out.println("Simbolo: " + simbolo);
            System.out.println("Estado: " + estado);
            System.out.println("Entrada: " + entrada);
            // Transición
            estado = tabla[estado][entrada];
            if (estado ==-1  || estado == 0) return false;
            System.out.println("NIUEVOEstado: " + estado);
            System.out.println("ACEPTACION== " + tabla[estado][3]);
        } while (simbolo != '#'); // UNTIL aceptar
        return tabla[estado][3] == 1; // CADENA ACEPTADA
    }
}
