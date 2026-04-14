package comp_B01;

public class TxtToAscii {
    public static void main(String[] args) {

        String palabra = "a";

        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            int ascii = (int) c;

            System.out.println(c + " -> " + ascii);
        }
    }
}
