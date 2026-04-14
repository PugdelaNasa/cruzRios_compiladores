package comp_T03;

import javax.swing.SwingUtilities;

public class main_DtIdValido {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DtIdValido ventana = new DtIdValido();
            ventana.setVisible(true);
        });

    }
}