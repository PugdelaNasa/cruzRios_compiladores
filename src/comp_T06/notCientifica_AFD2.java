/*
UNAM - FES ARAGON
Ingenieria en Computacion
Compiladores

-----------Programa 6 -----------------------------------
Codigo Version 2 AFD para notacion cientifica
Interfaz grafica en un solo archivo
*/

package comp_T06;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class notCientifica_AFD2 extends JFrame {

    private JTextArea areaTexto;
    private JTextField campoCadena;
    private JLabel etiquetaResultado;

    private int indice = 0;
    private String cadena = "";

    private final int error = -1;
    private final int aceptado = 1;

    public notCientifica_AFD2() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        add(panelPrincipal);

        JLabel titulo = new JLabel("AFD notación científica");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setForeground(new Color(40, 40, 40));

        panelPrincipal.add(Box.createVerticalStrut(30));
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createVerticalStrut(25));

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("SansSerif", Font.PLAIN, 16));
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setText("Aquí se mostrará el contenido del archivo .txt y\nmostrará línea por línea si es válido o inválido");

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(500, 360));
        scroll.setMaximumSize(new Dimension(500, 360));
        scroll.setMinimumSize(new Dimension(500, 360));

        panelPrincipal.add(scroll);
        panelPrincipal.add(Box.createVerticalStrut(20));

        JButton botonArchivo = new JButton("Seleccionar archivo .txt");
        botonArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonArchivo.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonArchivo.setPreferredSize(new Dimension(260, 40));
        botonArchivo.setMaximumSize(new Dimension(260, 40));
        botonArchivo.addActionListener(this::seleccionarArchivo);

        panelPrincipal.add(botonArchivo);
        panelPrincipal.add(Box.createVerticalStrut(28));

        JPanel panelCadena = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCadena.setBackground(new Color(245, 245, 245));

        JLabel etiquetaCadena = new JLabel("Cadena:");
        etiquetaCadena.setFont(new Font("SansSerif", Font.BOLD, 16));

        campoCadena = new JTextField();
        campoCadena.setPreferredSize(new Dimension(235, 35));
        campoCadena.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton botonProbar = new JButton("Probar cadena");
        botonProbar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonProbar.setPreferredSize(new Dimension(140, 35));
        botonProbar.addActionListener(this::probarCadena);

        panelCadena.add(etiquetaCadena);
        panelCadena.add(campoCadena);
        panelCadena.add(botonProbar);

        panelPrincipal.add(panelCadena);
        panelPrincipal.add(Box.createVerticalStrut(28));

        etiquetaResultado = new JLabel("Sin evaluar", SwingConstants.CENTER);
        etiquetaResultado.setFont(new Font("SansSerif", Font.BOLD, 16));
        etiquetaResultado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        etiquetaResultado.setPreferredSize(new Dimension(220, 50));
        etiquetaResultado.setMaximumSize(new Dimension(220, 50));
        etiquetaResultado.setMinimumSize(new Dimension(220, 50));
        etiquetaResultado.setOpaque(true);
        etiquetaResultado.setBackground(new Color(245, 245, 245));
        etiquetaResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPrincipal.add(etiquetaResultado);
    }

    private void seleccionarArchivo(ActionEvent e) {
        JFileChooser selector = new JFileChooser();
        int opcion = selector.showOpenDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            StringBuilder contenido = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;

                while ((linea = br.readLine()) != null) {
                    boolean valido = validarCadena(linea.trim());
                    contenido.append(linea)
                            .append(" ---> ")
                            .append(valido ? "Válida" : "Inválida")
                            .append("\n");
                }

                areaTexto.setText(contenido.toString());

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo");
            }
        }
    }

    private void probarCadena(ActionEvent e) {
        String texto = campoCadena.getText().trim();

        if (texto.isEmpty()) {
            etiquetaResultado.setText("Cadena vacía");
            return;
        }

        boolean valido = validarCadena(texto);

        if (valido) {
            etiquetaResultado.setText("Cadena válida");
        } else {
            etiquetaResultado.setText("Cadena inválida");
        }
    }

    public boolean validarCadena(String cadena) {
        this.cadena = cadena;
        this.indice = 0;
        int valor = estado_1();
        return valor == aceptado;
    }

    private char siguienteCaracter() {
        char caracter = '#';

        if (indice < cadena.length()) {
            caracter = cadena.charAt(indice);
            indice++;
        }

        return caracter;
    }

    private int estado_1() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_2();
        }
        return error;
    }

    private int estado_2() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_2();
        }

        switch (c) {
            case 'e':
                return estado_5();
            case '.':
                return estado_3();
            default:
                return error;
        }
    }

    private int estado_3() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_4();
        }

        return error;
    }

    private int estado_4() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_4();
        }

        switch (c) {
            case 'e':
                return estado_5();
            case '#':
                return aceptado;
            default:
                return error;
        }
    }

    private int estado_5() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_7();
        }

        switch (c) {
            case '+':
            case '-':
                return estado_6();
            default:
                return error;
        }
    }

    private int estado_6() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_7();
        }

        return error;
    }

    private int estado_7() {
        char c = siguienteCaracter();

        if (Character.isDigit(c)) {
            return estado_7();
        }

        switch (c) {
            case '#':
                return aceptado;
            default:
                return error;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new notCientifica_AFD2().setVisible(true);
        });
    }
}