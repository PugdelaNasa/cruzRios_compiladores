package comp_T01;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class id_lenguaje1 extends JFrame {

    private JTextPane cuadroSuperior;
    private JTextField txtCadena;
    private JLabel lblResultadoMini;

    public id_lenguaje1() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(430, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));

        JLabel titulo = new JLabel("AFD 00*00 con alfabeto");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBounds(110, 20, 220, 30);
        panelPrincipal.add(titulo);

        cuadroSuperior = new JTextPane();
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt y\nmostrará línea por línea si es válido o inválido");

        JScrollPane scroll = new JScrollPane(cuadroSuperior);
        scroll.setBounds(20, 60, 375, 310);
        scroll.setBorder(new LineBorder(Color.GRAY));
        panelPrincipal.add(scroll);

        JButton btnSeleccionar = new JButton("Seleccionar archivo .txt");
        btnSeleccionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSeleccionar.setBounds(105, 385, 210, 32);
        btnSeleccionar.addActionListener(e -> cargarArchivoTxt());
        panelPrincipal.add(btnSeleccionar);

        JLabel lblCadena = new JLabel("Cadena:");
        lblCadena.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblCadena.setBounds(20, 440, 70, 25);
        panelPrincipal.add(lblCadena);

        txtCadena = new JTextField();
        txtCadena.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtCadena.setBounds(90, 440, 190, 28);
        panelPrincipal.add(txtCadena);

        JButton btnProbar = new JButton("Probar cadena");
        btnProbar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnProbar.setBounds(285, 440, 110, 28);
        btnProbar.addActionListener(e -> probarCadena());
        panelPrincipal.add(btnProbar);

        JPanel panelMini = new JPanel();
        panelMini.setLayout(new BorderLayout());
        panelMini.setBackground(Color.WHITE);
        panelMini.setBorder(new LineBorder(Color.GRAY));
        panelMini.setBounds(120, 490, 170, 40);

        lblResultadoMini = new JLabel("Sin evaluar", SwingConstants.CENTER);
        lblResultadoMini.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelMini.add(lblResultadoMini, BorderLayout.CENTER);

        panelPrincipal.add(panelMini);

        setContentPane(panelPrincipal);
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

    private void probarCadena() {
        String cadena = txtCadena.getText().trim();

        if (cadena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa una cadena.",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean valida = validarCadena(cadena);

        StyledDocument doc = cuadroSuperior.getStyledDocument();

        Style estiloNormal = cuadroSuperior.addStyle("normalPrueba", null);
        StyleConstants.setForeground(estiloNormal, Color.BLACK);

        Style estiloValido = cuadroSuperior.addStyle("validoPrueba", null);
        StyleConstants.setForeground(estiloValido, new Color(0, 140, 0));
        StyleConstants.setBold(estiloValido, true);

        Style estiloInvalido = cuadroSuperior.addStyle("invalidoPrueba", null);
        StyleConstants.setForeground(estiloInvalido, Color.RED);
        StyleConstants.setBold(estiloInvalido, true);

        try {
            doc.insertString(doc.getLength(), "\nCadena \"" + cadena + "\" -> ", estiloNormal);

            if (valida) {
                doc.insertString(doc.getLength(), "VÁLIDA\n", estiloValido);
                lblResultadoMini.setText("VÁLIDO");
                lblResultadoMini.setForeground(new Color(0, 140, 0));
            } else {
                doc.insertString(doc.getLength(), "INVÁLIDA\n", estiloInvalido);
                lblResultadoMini.setText("INVÁLIDO");
                lblResultadoMini.setForeground(Color.RED);
            }

            cuadroSuperior.setCaretPosition(doc.getLength());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validarCadena(String cadena) {
        if (cadena == null || cadena.isEmpty()) return false;

        int estado = 0;
        int i = 0; // Tienes que manejar el índice por fuera

        do {
            char simbolo = cadena.charAt(i);

            switch (estado) {
                case 0:
                    if (simbolo == '0') estado = 1;
                    else return false;
                    break;
                case 1:
                    if (simbolo == '0') estado = 2;
                    else return false;
                    break;
                case 2:
                    if (simbolo == '1') estado = 3;
                    else return false;
                    break;
                case 3:
                    if (simbolo == '0') estado = 4;
                    else if (simbolo == '1') estado = 3;
                    else return false;
                    break;
                case 4:
                    if (simbolo == '0') estado = 5;
                    else if (simbolo == '1') estado = 3;
                    else return false;
                    break;
                case 5:
                    if (simbolo == '0') estado = 6;
                    else if (simbolo == '1') estado = 3;
                    else return false;
                    break;
                case 6:
                    if (simbolo == '0') estado = 6;
                    else return false;
                    break;
                default:
                    return false;
            }
            
            i++; // ¡No olvides esto o se cicla!
            
        } while (i < cadena.length()); 

        // Al final checamos si terminamos en estados de aceptación (según tu tabla)
        return estado == 2 || estado == 5 || estado == 6;
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
            doc.insertString(doc.getLength(), "Línea " + numLinea + ": " + linea + "   ", estiloNormal);

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