package comp_T01;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class id_lenguaje1 extends JFrame {

    private final JTextPane cuadroSuperior;
    private final JTextField txtCadena;
    private final JLabel lblMiniEstado;

    public id_lenguaje1() {
        setTitle("AFD 00*00");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(false);

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(220, 220, 220));

        JPanel celular = new JPanel();
        celular.setLayout(new BoxLayout(celular, BoxLayout.Y_AXIS));
        celular.setBackground(new Color(248, 248, 248));
        celular.setBorder(new CompoundBorder(
                new LineBorder(new Color(40, 40, 40), 4, true),
                new EmptyBorder(18, 18, 18, 18)
        ));
        celular.setPreferredSize(new Dimension(300, 620));

        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(new Color(248, 248, 248));
        barraSuperior.setMaximumSize(new Dimension(260, 20));
        barraSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JPanel notch = new JPanel();
        notch.setBackground(new Color(60, 60, 60));
        notch.setPreferredSize(new Dimension(90, 10));
        notch.setBorder(new LineBorder(new Color(60, 60, 60), 1, true));
        barraSuperior.add(notch);

        JLabel titulo = new JLabel("AFD 00*00 con alfabeto");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(new Color(35, 35, 35));

        cuadroSuperior = new JTextPane();
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setFont(new Font("Monospaced", Font.PLAIN, 13));
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt\n"
                + "y se indicará línea por línea si es válido o inválido.\n");

        JScrollPane scrollCuadroSuperior = new JScrollPane(cuadroSuperior);
        scrollCuadroSuperior.setPreferredSize(new Dimension(255, 300));
        scrollCuadroSuperior.setMaximumSize(new Dimension(255, 300));
        scrollCuadroSuperior.setMinimumSize(new Dimension(255, 300));
        scrollCuadroSuperior.setBorder(new LineBorder(Color.GRAY, 1, true));

        JButton botonSeleccionar = new JButton("Seleccionar archivo .txt");
        botonSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeleccionar.setFocusPainted(false);
        botonSeleccionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonSeleccionar.setMaximumSize(new Dimension(220, 35));
        botonSeleccionar.addActionListener(e -> cargarArchivoTxt());

        JLabel lblCadena = new JLabel("Cadena:");
        lblCadena.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCadena.setFont(new Font("SansSerif", Font.BOLD, 14));

        txtCadena = new JTextField();
        txtCadena.setMaximumSize(new Dimension(255, 35));
        txtCadena.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtCadena.setHorizontalAlignment(JTextField.CENTER);

        JButton botonProbar = new JButton("Probar cadena");
        botonProbar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonProbar.setFocusPainted(false);
        botonProbar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonProbar.setMaximumSize(new Dimension(170, 35));
        botonProbar.addActionListener(e -> probarCadenaManual());

        JPanel miniVentana = new JPanel();
        miniVentana.setLayout(new BorderLayout());
        miniVentana.setBackground(Color.WHITE);
        miniVentana.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                new EmptyBorder(8, 8, 8, 8)
        ));
        miniVentana.setMaximumSize(new Dimension(255, 55));
        miniVentana.setPreferredSize(new Dimension(255, 55));

        lblMiniEstado = new JLabel("Esperando cadena...", SwingConstants.CENTER);
        lblMiniEstado.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMiniEstado.setForeground(new Color(60, 60, 60));
        miniVentana.add(lblMiniEstado, BorderLayout.CENTER);

        celular.add(barraSuperior);
        celular.add(Box.createVerticalStrut(15));
        celular.add(titulo);
        celular.add(Box.createVerticalStrut(18));
        celular.add(scrollCuadroSuperior);
        celular.add(Box.createVerticalStrut(12));
        celular.add(botonSeleccionar);
        celular.add(Box.createVerticalStrut(18));
        celular.add(lblCadena);
        celular.add(Box.createVerticalStrut(5));
        celular.add(txtCadena);
        celular.add(Box.createVerticalStrut(12));
        celular.add(botonProbar);
        celular.add(Box.createVerticalStrut(15));
        celular.add(miniVentana);

        fondo.add(celular);
        setContentPane(fondo);
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
                int numLinea = 1;

                while ((linea = br.readLine()) != null) {
                    String cadena = linea.trim();
                    boolean valida = validarCadena(cadena);
                    imprimirLinea(numLinea, linea, valida);
                    numLinea++;
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

    private void probarCadenaManual() {
        String cadena = txtCadena.getText().trim();

        if (cadena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa una cadena para probar.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean valida = validarCadena(cadena);

        StyledDocument doc = cuadroSuperior.getStyledDocument();

        Style estiloNormal = cuadroSuperior.addStyle("normalManual", null);
        StyleConstants.setForeground(estiloNormal, Color.BLACK);
        StyleConstants.setBold(estiloNormal, true);

        Style estiloValido = cuadroSuperior.addStyle("validoManual", null);
        StyleConstants.setForeground(estiloValido, new Color(0, 140, 0));
        StyleConstants.setBold(estiloValido, true);

        Style estiloInvalido = cuadroSuperior.addStyle("invalidoManual", null);
        StyleConstants.setForeground(estiloInvalido, Color.RED);
        StyleConstants.setBold(estiloInvalido, true);

        try {
            doc.insertString(doc.getLength(), "\nCadena \"" + cadena + "\" -> ", estiloNormal);

            if (valida) {
                doc.insertString(doc.getLength(), "CADENA ACEPTADA\n", estiloValido);
                lblMiniEstado.setText("VÁLIDO");
                lblMiniEstado.setForeground(new Color(0, 140, 0));
            } else {
                doc.insertString(doc.getLength(), "CADENA INVÁLIDA\n", estiloInvalido);
                lblMiniEstado.setText("INVÁLIDO");
                lblMiniEstado.setForeground(Color.RED);
            }

            cuadroSuperior.setCaretPosition(doc.getLength());

        } catch (Exception e) {
            e.printStackTrace();
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
                        if (simbolo == '0') {
                            estado = 1;
                        } else {
                            throw new Exception("Error en estado 0: se esperaba 0");
                        }
                        break;

                    case 1:
                        if (simbolo == '0') {
                            estado = 2;
                        } else {
                            throw new Exception("Error en estado 1: se esperaba 0");
                        }
                        break;

                    case 2:
                        if (simbolo == '0') {
                            estado = 3;
                        } else {
                            throw new Exception("Error en estado 2: se esperaba 0");
                        }
                        break;

                    case 3:
                        if (simbolo == '0') {
                            estado = 3;
                        } else {
                            throw new Exception("Error en estado 3: solo ceros permitidos");
                        }
                        break;

                    default:
                        throw new Exception("Estado inválido");
                }
            }

            return estado == 3;

        } catch (Exception e) {
            System.out.println("Rutina error: " + e.getMessage());
            return false;
        }
    }

    private void imprimirLinea(int numLinea, String linea, boolean valida) {
        StyledDocument doc = cuadroSuperior.getStyledDocument();

        Style estiloNormal = cuadroSuperior.addStyle("normalArchivo", null);
        StyleConstants.setForeground(estiloNormal, Color.BLACK);

        Style estiloValido = cuadroSuperior.addStyle("validoArchivo", null);
        StyleConstants.setForeground(estiloValido, new Color(0, 150, 0));
        StyleConstants.setBold(estiloValido, true);

        Style estiloInvalido = cuadroSuperior.addStyle("invalidoArchivo", null);
        StyleConstants.setForeground(estiloInvalido, Color.RED);
        StyleConstants.setBold(estiloInvalido, true);

        try {
            doc.insertString(doc.getLength(), "Línea " + numLinea + ": " + linea + "    ", estiloNormal);

            if (valida) {
                doc.insertString(doc.getLength(), "VÁLIDO\n", estiloValido);
            } else {
                doc.insertString(doc.getLength(), "INVÁLIDO\n", estiloInvalido);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new id_lenguaje1().setVisible(true));
    }
}