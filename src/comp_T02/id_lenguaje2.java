package comp_T02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
/*
-----------Programa 2 -----------------------------------
Version 1 de AFD *aa* con alfabeto ab
*/

import java.io.*;

public class id_lenguaje2 extends JFrame {

    private final JTextPane cuadroSuperior;

    public id_lenguaje2() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel(" AFD *aa* con alfabeto ab ");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        cuadroSuperior = new JTextPane();
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt y mostrará línea por línea si es válido o inválido");

        JScrollPane scrollCuadroSuperior = new JScrollPane(cuadroSuperior);
        scrollCuadroSuperior.setPreferredSize(new Dimension(300, 250));
        scrollCuadroSuperior.setMaximumSize(new Dimension(300, 250));
        scrollCuadroSuperior.setMinimumSize(new Dimension(300, 250));

        JButton botonSeleccionar = new JButton("Seleccionar archivo .txt");
        botonSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeleccionar.addActionListener(e -> cargarArchivoTxt());

        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(scrollCuadroSuperior);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(botonSeleccionar);
        contenido.add(Box.createVerticalStrut(20));

        setContentPane(contenido);
    }

    private void cargarArchivoTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo .txt");

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!archivo.getName().toLowerCase().endsWith(".txt")) {
                JOptionPane.showMessageDialog(this,
                        "Solo se permiten archivos .txt",
                        "Archivo inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

                cuadroSuperior.setText("");
                String linea;
                int numLinea = 1;

                while ((linea = br.readLine()) != null) {
                    String cadena = linea.trim();
                    boolean valida = validarCadena(cadena);
                    imprimirLinea(numLinea, linea, valida);
                    numLinea++;
                }

                cuadroSuperior.setCaretPosition(0);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo leer el archivo: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarCadena(String cadena) {
        try {
            if (cadena == null || cadena.isEmpty()) {
                throw new Exception("Cadena vacía");
            }

            int estado = 0;

            for (int i = 0; i < cadena.length(); i++) {
                char simbolo = cadena.charAt(i);

                switch (estado) {

                    case 0:
                        // AQUÍ VA TU LÓGICA DEL id_lenguaje2
                        // Ejemplo:
                        if (simbolo == 'a') {
                            estado = 1;
                        } else {
                            throw new Exception("Error en estado 0");
                        }
                        break;

                    case 1:
                        // AQUÍ VA TU LÓGICA
                        if (simbolo == 'b') {
                            estado = 2;
                        } else {
                            throw new Exception("Error en estado 1");
                        }
                        break;

                    case 2:
                        // AQUÍ VA TU LÓGICA
                        if (simbolo == 'c') {
                            estado = 3;
                        } else {
                            throw new Exception("Error en estado 2");
                        }
                        break;

                    case 3:
                        // AQUÍ VA TU LÓGICA
                        if (simbolo == 'c') {
                            estado = 3;
                        } else {
                            throw new Exception("Error en estado 3");
                        }
                        break;

                    default:
                        throw new Exception("Estado inválido");
                }
            }

            // CAMBIA ESTE ESTADO FINAL SEGÚN TU AUTÓMATA
            return estado == 3;

        } catch (Exception e) {
            System.out.println("Rutina error: " + e.getMessage());
            return false;
        }
    }

    private void imprimirLinea(int numLinea, String linea, boolean valida) {
        StyledDocument doc = cuadroSuperior.getStyledDocument();

        Style estiloNormal = cuadroSuperior.addStyle("normal", null);
        StyleConstants.setForeground(estiloNormal, Color.BLACK);

        Style estiloValido = cuadroSuperior.addStyle("valido", null);
        StyleConstants.setForeground(estiloValido, new Color(0, 150, 0));

        Style estiloInvalido = cuadroSuperior.addStyle("invalido", null);
        StyleConstants.setForeground(estiloInvalido, Color.RED);

        try {
            doc.insertString(doc.getLength(), "Línea " + numLinea + ": " + linea + "    ", estiloNormal);

            if (valida) {
                doc.insertString(doc.getLength(), "VALIDO\n", estiloValido);
            } else {
                doc.insertString(doc.getLength(), "INVALIDO\n", estiloInvalido);
            }

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new id_lenguaje2().setVisible(true));
    }
}