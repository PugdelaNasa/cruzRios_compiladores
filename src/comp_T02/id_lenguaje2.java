package comp_T02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;

public class id_lenguaje2 extends JFrame {

    private final JTextPane cuadroSuperior;
    private JTextField campoCadena;
    private JLabel resultadoEvaluacion;

    public id_lenguaje2() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(25, 25, 25, 25));
        contenido.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("AFD *aa* con alfabeto ab");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(new Color(40, 40, 40));

        cuadroSuperior = new JTextPane();
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt y mostrará línea por línea si es válido o inválido");

        JScrollPane scrollCuadroSuperior = new JScrollPane(cuadroSuperior);
        scrollCuadroSuperior.setPreferredSize(new Dimension(470, 420));
        scrollCuadroSuperior.setMaximumSize(new Dimension(470, 420));
        scrollCuadroSuperior.setMinimumSize(new Dimension(470, 420));

        JButton botonSeleccionar = new JButton("Seleccionar archivo .txt");
        botonSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeleccionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonSeleccionar.setPreferredSize(new Dimension(220, 40));
        botonSeleccionar.setMaximumSize(new Dimension(220, 40));
        botonSeleccionar.addActionListener(e -> cargarArchivoTxt());

        JPanel panelCadena = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCadena.setOpaque(false);
        panelCadena.setMaximumSize(new Dimension(470, 50));

        JLabel etiquetaCadena = new JLabel("Cadena:");
        etiquetaCadena.setFont(new Font("SansSerif", Font.BOLD, 16));

        campoCadena = new JTextField();
        campoCadena.setPreferredSize(new Dimension(235, 34));
        campoCadena.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JButton botonProbar = new JButton("Probar cadena");
        botonProbar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonProbar.setPreferredSize(new Dimension(140, 34));
        botonProbar.addActionListener(e -> probarCadenaManual());

        panelCadena.add(etiquetaCadena);
        panelCadena.add(campoCadena);
        panelCadena.add(botonProbar);

        JPanel panelResultado = new JPanel();
        panelResultado.setOpaque(false);
        panelResultado.setMaximumSize(new Dimension(470, 80));

        resultadoEvaluacion = new JLabel("Sin evaluar", SwingConstants.CENTER);
        resultadoEvaluacion.setOpaque(true);
        resultadoEvaluacion.setBackground(new Color(245, 245, 245));
        resultadoEvaluacion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultadoEvaluacion.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultadoEvaluacion.setPreferredSize(new Dimension(210, 55));

        panelResultado.add(resultadoEvaluacion);

        contenido.add(Box.createVerticalStrut(10));
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(scrollCuadroSuperior);
        contenido.add(Box.createVerticalStrut(18));
        contenido.add(botonSeleccionar);
        contenido.add(Box.createVerticalStrut(28));
        contenido.add(panelCadena);
        contenido.add(Box.createVerticalStrut(25));
        contenido.add(panelResultado);

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

    private void probarCadenaManual() {
        String cadena = campoCadena.getText().trim();

        if (cadena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Escribe una cadena para evaluar.",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean valida = validarCadena(cadena);

        if (valida) {
            resultadoEvaluacion.setText("VÁLIDO");
            resultadoEvaluacion.setForeground(new Color(0, 130, 0));
        } else {
            resultadoEvaluacion.setText("INVÁLIDO");
            resultadoEvaluacion.setForeground(Color.RED);
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
                        // AQUÍ VA TU LÓGICA REAL DEL id_lenguaje2
                        if (simbolo == 'a') {
                            estado = 1;
                        }else if (simbolo == 'b') estado = 0;
                        else {
                            throw new Exception("Error en estado 0");
                        }
                        break;

                    case 1:
                        if (simbolo == 'a') {
                            estado = 2;
                        } else if (simbolo == 'b') {
                            estado = 0;
                        } else {
                            throw new Exception("Error en estado 1");
                        }
                        break;

                    case 2:
                        if (simbolo == 'a') {
                            estado = 2;
                        } else if (simbolo == 'b') {
                            estado = 0;
                        } else {
                            throw new Exception("Error en estado 2");
                        }
                        break;

                    default:
                        throw new Exception("Estado inválido");
                }
            }

            // CAMBIA EL ESTADO FINAL SEGÚN TU AUTÓMATA
            return estado == 2;

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
        StyleConstants.setBold(estiloValido, true);

        Style estiloInvalido = cuadroSuperior.addStyle("invalido", null);
        StyleConstants.setForeground(estiloInvalido, Color.RED);
        StyleConstants.setBold(estiloInvalido, true);

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