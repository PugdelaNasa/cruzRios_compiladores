package comp_B01;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class IdValido {

    public static void main(String[] args) {

        String ruta = "C:/Users/janca/OneDrive/Documentos/prueba.txt";

        try {

            List<String> lineas = Files.readAllLines(Paths.get(ruta));

            int numLinea = 1;

            for (String linea : lineas) {

                int estado = 1;
                boolean error = false;

                for (int i = 0; i < linea.length(); i++) {

                    char c = linea.charAt(i);
                    int ascii = (int) c;

                    boolean esLetra =
                            (ascii >= 65 && ascii <= 90) ||
                                    (ascii >= 97 && ascii <= 122);

                    boolean esNumero =
                            (ascii >= 48 && ascii <= 57);

                    switch (estado) {

                        case 1:
                            if (esLetra) estado = 3;
                            else if (esNumero) {
                                error = true; // ⛔ inicia con número
                            }
                            else error = true;
                            break;

                        case 3:
                            if (esLetra || esNumero) estado = 3;
                            else error = true;
                            break;
                    }

                    if (error) break;
                }

                if (!error && estado == 3)
                    System.out.println("Linea " + numLinea + " -> VALIDA");
                else
                    System.out.println("Linea " + numLinea + " -> INVALIDA");

                numLinea++;
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }
    }
}