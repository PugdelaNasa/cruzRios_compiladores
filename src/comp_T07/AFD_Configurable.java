package comp_T07;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class AFD_Configurable extends JFrame {

    // ======= COMPONENTES DE CAPTURA =======
    private JTextField txtSigma;
    private JTextField txtEstados;
    private JTextField txtEstadoInicial;
    private JTextField txtEstadosFinales;

    private JButton btnGenerarTabla;
    private JButton btnCompletarTabla;
    private JButton btnSeleccionarArchivo;
    private JButton btnProbarCadena;

    // ======= TABLA VISUAL =======
    private JTable tablaTransiciones;
    private DefaultTableModel modeloTabla;

    // ======= AREAS =======
    private JTextArea areaArchivo;
    private JTextPane areaResultado;
    private JTextField txtCadenaPrueba;

    // ======= DATOS INTERNOS DEL AFD =======
    private static String[] alfabeto;
    private static String[] nombresEstados;
    private static int[][] tabla;
    private static int estadoInicialIndice;

    public AFD_Configurable() {
        setTitle("AFD CONFIGURABLE");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("AFD CONFIGURABLE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentral.add(crearPanelConfiguracion());
        panelCentral.add(crearPanelTabla());
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelInferior.add(crearPanelArchivo());
        panelInferior.add(crearPanelResultado());
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelConfiguracion() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Ingresar datos AFD"));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSigma = new JLabel("Σ (símbolos):");
        JLabel lblEstados = new JLabel("Q (estados):");
        JLabel lblInicial = new JLabel("q0:");
        JLabel lblFinales = new JLabel("F:");

        txtSigma = new JTextField("0,1", 20);
        txtEstados = new JTextField("q0,q1,q2", 20);
        txtEstadoInicial = new JTextField("q0", 20);
        txtEstadosFinales = new JTextField("q2", 20);

        btnGenerarTabla = new JButton("Generar tabla");
        btnGenerarTabla.addActionListener(e -> generarTabla());

        JLabel ayuda = new JLabel("<html>"
                + "Ejemplo:<br>"
                + "Σ = 0,1<br>"
                + "Q = q0,q1,q2<br>"
                + "q0 = q0<br>"
                + "F = q2"
                + "</html>");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblSigma, gbc);
        gbc.gridx = 1;
        panel.add(txtSigma, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblEstados, gbc);
        gbc.gridx = 1;
        panel.add(txtEstados, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblInicial, gbc);
        gbc.gridx = 1;
        panel.add(txtEstadoInicial, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblFinales, gbc);
        gbc.gridx = 1;
        panel.add(txtEstadosFinales, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnGenerarTabla, gbc);

        gbc.gridy = 5;
        panel.add(ayuda, gbc);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Tabla de transición"));

        modeloTabla = new DefaultTableModel();
        tablaTransiciones = new JTable(modeloTabla);
        tablaTransiciones.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tablaTransiciones);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnCompletarTabla = new JButton("Completar tabla");
        btnCompletarTabla.setEnabled(false);
        btnCompletarTabla.addActionListener(e -> completarTabla());

        txtCadenaPrueba = new JTextField(20);

        btnProbarCadena = new JButton("Probar cadena");
        btnProbarCadena.setEnabled(false);
        btnProbarCadena.addActionListener(e -> probarCadenaManual());

        panelBotones.add(btnCompletarTabla);
        panelBotones.add(new JLabel("Cadena:"));
        panelBotones.add(txtCadenaPrueba);
        panelBotones.add(btnProbarCadena);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelArchivo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Vista archivo TXT"));

        areaArchivo = new JTextArea();
        areaArchivo.setEditable(false);
        areaArchivo.setLineWrap(true);
        areaArchivo.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaArchivo);
        panel.add(scroll, BorderLayout.CENTER);

        btnSeleccionarArchivo = new JButton("Seleccionar archivo .txt");
        btnSeleccionarArchivo.setEnabled(false);
        btnSeleccionarArchivo.addActionListener(e -> abrirArchivo());

        JPanel abajo = new JPanel();
        abajo.add(btnSeleccionarArchivo);
        panel.add(abajo, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(500, 220));
        return panel;
    }

    private JPanel crearPanelResultado() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Resultado del análisis"));

        areaResultado = new JTextPane();
        areaResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultado);
        panel.add(scroll, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(500, 220));
        return panel;
    }

    // ===================== GENERAR TABLA =====================
    private void generarTabla() {
        try {
            alfabeto = separar(txtSigma.getText());
            nombresEstados = separar(txtEstados.getText());

            if (alfabeto.length == 0 || nombresEstados.length == 0) {
                throw new IllegalArgumentException("Σ y Q no pueden estar vacíos.");
            }

            String estadoInicialTexto = txtEstadoInicial.getText().trim();
            estadoInicialIndice = buscarIndiceEstado(estadoInicialTexto);

            if (estadoInicialIndice == -1) {
                throw new IllegalArgumentException("El estado inicial no pertenece a Q.");
            }

            String[] finales = separar(txtEstadosFinales.getText());

            for (int i = 0; i < finales.length; i++) {
                if (buscarIndiceEstado(finales[i]) == -1) {
                    throw new IllegalArgumentException("El estado final \"" + finales[i] + "\" no pertenece a Q.");
                }
            }

            Vector<String> columnas = new Vector<>();
            columnas.add("Estado");

            for (int i = 0; i < alfabeto.length; i++) {
                columnas.add(alfabeto[i]);
            }

            columnas.add("¿Final?");

            Vector<Vector<Object>> datos = new Vector<>();

            for (int i = 0; i < nombresEstados.length; i++) {
                Vector<Object> fila = new Vector<>();
                fila.add(nombresEstados[i]);

                for (int j = 0; j < alfabeto.length; j++) {
                    fila.add("");
                }

                fila.add(esFinal(nombresEstados[i], finales) ? "1" : "0");
                datos.add(fila);
            }

            modeloTabla = new DefaultTableModel(datos, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column != 0;
                }
            };

            tablaTransiciones.setModel(modeloTabla);

            for (int i = 0; i < tablaTransiciones.getColumnModel().getColumnCount(); i++) {
                TableColumn col = tablaTransiciones.getColumnModel().getColumn(i);
                col.setPreferredWidth(90);
            }

            tabla = null;
            btnCompletarTabla.setEnabled(true);
            btnProbarCadena.setEnabled(true);

            areaResultado.setText("");
            escribirResultado("Tabla generada correctamente.\n", new Color(0, 120, 0));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== COMPLETAR TABLA =====================
    private void completarTabla() {
        try {
            if (tablaTransiciones.isEditing()) {
                tablaTransiciones.getCellEditor().stopCellEditing();
            }

            int numEstados = nombresEstados.length;
            int numSimbolos = alfabeto.length;

            // columnas internas:
            // 0 = #
            // 1..numSimbolos = símbolos
            // numSimbolos+1 = aceptación
            tabla = new int[numEstados][numSimbolos + 2];

            for (int i = 0; i < numEstados; i++) {

                // transición con # al final: no se usa para avanzar, se deja en el mismo estado
                tabla[i][0] = i;

                for (int j = 0; j < numSimbolos; j++) {
                    Object valor = modeloTabla.getValueAt(i, j + 1);
                    String estadoDestino = "";

                    if (valor != null) {
                        estadoDestino = valor.toString().trim();
                    }

                    if (estadoDestino.isEmpty()) {
                        tabla[i][j + 1] = -1; // vacío = error
                    } else {
                        int indiceDestino = buscarIndiceEstado(estadoDestino);

                        if (indiceDestino == -1) {
                            throw new IllegalArgumentException(
                                    "El estado destino \"" + estadoDestino + "\" no pertenece a Q."
                            );
                        }

                        tabla[i][j + 1] = indiceDestino;
                    }
                }

                Object valorFinal = modeloTabla.getValueAt(i, numSimbolos + 1);
                String marcaFinal = "0";

                if (valorFinal != null && !valorFinal.toString().trim().isEmpty()) {
                    marcaFinal = valorFinal.toString().trim();
                }

                if (!marcaFinal.equals("0") && !marcaFinal.equals("1")) {
                    throw new IllegalArgumentException(
                            "La columna ¿Final? solo acepta 0 o 1 en el estado " + nombresEstados[i]
                    );
                }

                tabla[i][numSimbolos + 1] = Integer.parseInt(marcaFinal);
            }

            btnSeleccionarArchivo.setEnabled(true);

            areaResultado.setText("");
            escribirResultado("Tabla actualizada correctamente.\n", new Color(0, 120, 0));
            escribirResultado("Las celdas vacías se toman como estado de error.\n", new Color(0, 120, 0));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en tabla", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== PROBAR CADENA MANUAL =====================
    private void probarCadenaManual() {
        if (tablaTransiciones.isEditing()) {
            tablaTransiciones.getCellEditor().stopCellEditing();
        }

        if (tabla == null) {
            JOptionPane.showMessageDialog(this, "Primero completa la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cadena = txtCadenaPrueba.getText();
        if (cadena == null) {
            cadena = "";
        }

        areaResultado.setText("");

        if (analizarCadena(cadena)) {
            escribirResultado("Cadena \"" + cadena + "\" -> CADENA ACEPTADA\n", new Color(0, 150, 0));
        } else {
            escribirResultado("Cadena \"" + cadena + "\" -> CADENA NO VALIDA\n", new Color(200, 0, 0));
        }
    }

    // ===================== ABRIR ARCHIVO =====================
    private void abrirArchivo() {
        JFileChooser selector = new JFileChooser();
        int opcion = selector.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            areaArchivo.setText("");
            areaResultado.setText("");

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int numLinea = 1;

                while ((linea = br.readLine()) != null) {
                    areaArchivo.append(linea + "\n");

                    if (analizarCadena(linea.trim())) {
                        escribirResultado(
                                "Línea " + numLinea + ": \"" + linea + "\" -> CADENA ACEPTADA\n",
                                new Color(0, 150, 0)
                        );
                    } else {
                        escribirResultado(
                                "Línea " + numLinea + ": \"" + linea + "\" -> CADENA NO VALIDA\n",
                                new Color(200, 0, 0)
                        );
                    }

                    numLinea++;
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== ANALIZAR CADENA =====================
    public static boolean analizarCadena(String cadena) {

        int estado = estadoInicialIndice;
        int entrada;
        int i = 0;
        char simbolo;

        if (tabla == null || alfabeto == null || nombresEstados == null) {
            return false;
        }

        do {

            if (i < cadena.length()) {
                simbolo = cadena.charAt(i);
            } else {
                simbolo = '#';
            }

            i++;

            entrada = obtenerEntrada(simbolo);

            if (entrada == -1) {
                return false;
            }

            estado = tabla[estado][entrada];

            // solo -1 es error
            if (estado == -1) {
                return false;
            }

        } while (simbolo != '#');

        return tabla[estado][alfabeto.length + 1] == 1;
    }

    // ===================== UTILIDADES =====================
    private static int obtenerEntrada(char simbolo) {

        if (simbolo == '#') {
            return 0;
        }

        for (int j = 0; j < alfabeto.length; j++) {
            if (alfabeto[j].equals(String.valueOf(simbolo))) {
                return j + 1;
            }
        }

        return -1;
    }

    private int buscarIndiceEstado(String nombreEstado) {
        for (int i = 0; i < nombresEstados.length; i++) {
            if (nombresEstados[i].equals(nombreEstado)) {
                return i;
            }
        }
        return -1;
    }

    private String[] separar(String texto) {
        texto = texto.trim();

        if (texto.isEmpty()) {
            return new String[0];
        }

        String[] partes = texto.split(",");
        int contador = 0;

        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim();
            if (!partes[i].isEmpty()) {
                contador++;
            }
        }

        String[] resultado = new String[contador];
        int k = 0;

        for (int i = 0; i < partes.length; i++) {
            if (!partes[i].isEmpty()) {
                resultado[k] = partes[i];
                k++;
            }
        }

        return resultado;
    }

    private boolean esFinal(String estado, String[] finales) {
        for (int i = 0; i < finales.length; i++) {
            if (estado.equals(finales[i])) {
                return true;
            }
        }
        return false;
    }

    private void escribirResultado(String texto, Color color) {
        StyledDocument doc = areaResultado.getStyledDocument();
        Style estilo = areaResultado.addStyle("estilo", null);
        StyleConstants.setForeground(estilo, color);

        try {
            doc.insertString(doc.getLength(), texto, estilo);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AFD_Configurable().setVisible(true));
    }
}