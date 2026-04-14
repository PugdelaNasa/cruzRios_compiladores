package comp_N01;

import javax.swing.*;
import java.awt.*;


public class AFD1 extends JFrame {

    public AFD1() {

        setTitle("Analizador AFD 00(0|1)*00");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));
        setLocationRelativeTo(null);

        JLabel lblInstruccion = new JLabel("Introduce la cadena (solo 0s y 1s):", SwingConstants.CENTER);
        JTextField txtCadena = new JTextField();
        JButton btnValidar = new JButton("Validar");
        JLabel lblResultado = new JLabel("........", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));


        add(lblInstruccion);
        add(txtCadena);
        add(btnValidar);
        add(lblResultado);


        btnValidar.addActionListener(e -> {
            String cadena = txtCadena.getText();


            if (escanear(cadena)) {
                lblResultado.setText("RESULTADO: Cadena Aceptada");
                lblResultado.setForeground(new Color(0, 120, 0)); // Verde
            } else {
                lblResultado.setText("RESULTADO: Cadena Rechazada");
                lblResultado.setForeground(Color.RED);
            }
        });
    }


    public static boolean escanear(String cadena) {
        if (cadena == null || cadena.isEmpty()) return false;
        char estado = 'A';

        for (int i = 0; i < cadena.length(); i++) {
            char simbolo = cadena.charAt(i);

            switch (estado) {
                case 'A':
                    if (simbolo == '0') estado = 'B';
                    else return false;
                    break;
                case 'B':
                    if (simbolo == '0') estado = 'C';
                    else return false;
                    break;
                case 'C':
                    if (simbolo == '0') estado = 'D';
                    else if (simbolo == '1') estado = 'C';
                    else return false;
                    break;
                case 'D':
                    if (simbolo == '0') estado = 'E';
                    else if (simbolo == '1') estado = 'C';
                    else return false;
                    break;
                case 'E':
                    if (simbolo == '0') estado = 'E';
                    else if (simbolo == '1') estado = 'C';
                    else return false;
                    break;
            }
        }
        return estado == 'E';
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new AFD1().setVisible(true);
        });
    }
}