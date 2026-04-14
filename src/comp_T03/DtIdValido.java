package comp_T03;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class DtIdValido extends JFrame {

    private final JTextPane cuadroSuperior;

    public DtIdValido() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Identificador");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        cuadroSuperior = new JTextPane();
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt y mostrara linea por linea si es valido o invalido");

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

            try (java.io.BufferedReader br = Files.newBufferedReader(
                    archivo.toPath(), StandardCharsets.UTF_8)) {

                cuadroSuperior.setText("");
                String linea;

                while ((linea = br.readLine()) != null) {

                    boolean valida = esValida(linea);
                    imprimirLinea(linea, valida);

                }

                cuadroSuperior.setCaretPosition(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo leer el archivo: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 🔹 AUTÓMATA
    private boolean esValida(String linea) {

        if (linea.isEmpty()) return false;

        int estado = 1;

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
                    else return false; // ❌ si inicia con número → inválido
                    break;

                case 3:
                    if (esLetra || esNumero) estado = 3;
                    else return false;
                    break;
            }
        }

        return estado == 3;
    }

    // 🔹 Impresión con color
    private void imprimirLinea(String linea, boolean valida) {
        StyledDocument doc = cuadroSuperior.getStyledDocument();

        Style estiloNormal = cuadroSuperior.addStyle("normal", null);
        StyleConstants.setForeground(estiloNormal, Color.BLACK);

        Style estiloValido = cuadroSuperior.addStyle("valido", null);
        StyleConstants.setForeground(estiloValido, new Color(0, 150, 0));

        Style estiloInvalido = cuadroSuperior.addStyle("invalido", null);
        StyleConstants.setForeground(estiloInvalido, Color.RED);

        try {
            doc.insertString(doc.getLength(), linea + "    ", estiloNormal);

            if (valida)
                doc.insertString(doc.getLength(), "VALIDO\n", estiloValido);
            else
                doc.insertString(doc.getLength(), "INVALIDO\n", estiloInvalido);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
