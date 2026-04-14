package comp_B01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtToAscii_v2 {

    public static void main(String[] args) {

        String ruta = "C:\\Users\\janca\\OneDrive\\Documentos\\pruebaAscii.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = br.readLine()) != null) {

                System.out.println("Texto leído: " + linea);
                System.out.print("ASCII: ");


                for (char c : linea.toCharArray()) {
                    System.out.print((int)c + " ");
                }

                System.out.println("\n");
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
    }
}
