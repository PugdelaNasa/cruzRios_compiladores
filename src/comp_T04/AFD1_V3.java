package comp_T04;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;

public class AFD1_V3 extends JFrame {

    // TABLA DEL AFD
    static int[][] tabla = {
            {0, 1, -1, 0},
            {1, 2, -1, 0},
            {2, -1, 3, 1},
            {3, 4, 3, 0},
            {4, 5, 3, 0},
            {5, 6, 3, 1},
            {6, 6, -1, 1}
    };

    private JTextPane cuadroSuperior;
    private JTextField campoCadena;
    private JLabel etiquetaResultado;

    public AFD1_V3() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("AFD 00*00 con alfabeto");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setForeground(new Color(40, 40, 40));

        contenido.add(Box.createVerticalStrut(20));
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(20));

        cuadroSuperior = new JTextPane();
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setFont(new Font("Serif", Font.PLAIN, 18));
        cuadroSuperior.setBackground(Color.WHITE);
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt y\nmostrará línea por línea si es válido o inválido");

        JScrollPane scroll = new JScrollPane(cuadroSuperior);
        scroll.setPreferredSize(new Dimension(470, 390));
        scroll.setMaximumSize(new Dimension(470, 390));
        scroll.setMinimumSize(new Dimension(470, 390));

        contenido.add(scroll);
        contenido.add(Box.createVerticalStrut(20));

        JButton botonArchivo = new JButton("Seleccionar archivo .txt");
        botonArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonArchivo.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonArchivo.setPreferredSize(new Dimension(220, 40));
        botonArchivo.setMaximumSize(new Dimension(260, 40));
        botonArchivo.addActionListener(e -> abrirArchivo());

        contenido.add(botonArchivo);
        contenido.add(Box.createVerticalStrut(28));

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
        panelInferior.setBackground(new Color(245, 245, 245));
        panelInferior.setMaximumSize(new Dimension(470, 40));

        JLabel lblCadena = new JLabel("Cadena:");
        lblCadena.setFont(new Font("SansSerif", Font.BOLD, 16));

        campoCadena = new JTextField();
        campoCadena.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoCadena.setMaximumSize(new Dimension(260, 34));
        campoCadena.setPreferredSize(new Dimension(260, 34));

        JButton botonProbar = new JButton("Probar ca...");
        botonProbar.setFont(new Font("SansSerif", Font.BOLD, 13));
        botonProbar.addActionListener(e -> probarCadena());

        panelInferior.add(lblCadena);
        panelInferior.add(Box.createHorizontalStrut(15));
        panelInferior.add(campoCadena);
        panelInferior.add(Box.createHorizontalStrut(10));
        panelInferior.add(botonProbar);

        contenido.add(panelInferior);
        contenido.add(Box.createVerticalStrut(28));

        etiquetaResultado = new JLabel(" ");
        etiquetaResultado.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaResultado.setOpaque(true);
        etiquetaResultado.setBackground(Color.WHITE);
        etiquetaResultado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        etiquetaResultado.setFont(new Font("SansSerif", Font.BOLD, 18));
        etiquetaResultado.setForeground(Color.RED);
        etiquetaResultado.setPreferredSize(new Dimension(210, 48));
        etiquetaResultado.setMaximumSize(new Dimension(210, 48));
        etiquetaResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenido.add(etiquetaResultado);

        add(contenido);
    }

    private void abrirArchivo() {
        JFileChooser selector = new JFileChooser();
        int opcion = selector.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt y\nmostrará línea por línea si es válido o inválido\n");

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    mostrarResultadoLinea(linea);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo leer el archivo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void probarCadena() {
        String cadena = campoCadena.getText().trim();

        if (cadena.isEmpty()) {
            etiquetaResultado.setText("INVÁLIDO");
            etiquetaResultado.setForeground(Color.RED);
            agregarLineaResultado("Cadena vacía -> INVÁLIDA", false);
            return;
        }

        boolean valida = analizarCadena(cadena);

        if (valida) {
            etiquetaResultado.setText("VÁLIDO");
            etiquetaResultado.setForeground(new Color(0, 140, 0));
        } else {
            etiquetaResultado.setText("INVÁLIDO");
            etiquetaResultado.setForeground(Color.RED);
        }

        agregarLineaResultado("Cadena \"" + cadena + "\" -> " + (valida ? "VÁLIDA" : "INVÁLIDA"), valida);
    }

    private void mostrarResultadoLinea(String linea) {
        boolean valida = analizarCadena(linea.trim());
        agregarLineaResultado("Cadena \"" + linea + "\" -> " + (valida ? "VÁLIDA" : "INVÁLIDA"), valida);
    }

    private void agregarLineaResultado(String texto, boolean valida) {
        StyledDocument doc = cuadroSuperior.getStyledDocument();

        try {
            Style negro = cuadroSuperior.addStyle("negro", null);
            StyleConstants.setForeground(negro, Color.BLACK);
            StyleConstants.setFontSize(negro, 18);

            Style color = cuadroSuperior.addStyle("color", null);
            StyleConstants.setForeground(color, valida ? new Color(0, 140, 0) : Color.RED);
            StyleConstants.setBold(color, true);
            StyleConstants.setFontSize(color, 18);

            String[] partes = texto.split("->");

            if (partes.length == 2) {
                doc.insertString(doc.getLength(), partes[0] + "-> ", negro);
                doc.insertString(doc.getLength(), partes[1] + "\n", color);
            } else {
                doc.insertString(doc.getLength(), texto + "\n", negro);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    static boolean analizarCadena(String cadena) {
        int estado = 0;
        int entrada;
        int i = 0;
        char simbolo;

        do {
            if (i < cadena.length()) {
                simbolo = cadena.charAt(i);
            } else {
                simbolo = '#';
            }

            i++;

            if (simbolo == '0') {
                entrada = 1;
            } else if (simbolo == '1') {
                entrada = 2;
            } else if (simbolo == '#') {
                entrada = 0;
            } else {
                return false;
            }

            estado = tabla[estado][entrada];

            if (estado == -1 || estado == 0) {
                return false;
            }

        } while (simbolo != '#');

        return tabla[estado][3] == 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AFD1_V3().setVisible(true));
    }
}