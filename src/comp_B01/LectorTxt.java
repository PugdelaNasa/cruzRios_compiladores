package comp_B01;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LectorTxt extends JFrame {

    private final JTextArea cuadroSuperior;

    public LectorTxt() {
        setTitle("Abrir Txt de Texto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 740);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        contenido.setBackground(new Color(245, 245, 245));

        JLabel titulo = new JLabel("Vista de archivo TXT");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        cuadroSuperior = new JTextArea();
        cuadroSuperior.setLineWrap(true);
        cuadroSuperior.setWrapStyleWord(true);
        cuadroSuperior.setEditable(false);
        cuadroSuperior.setText("Aquí se mostrará el contenido del archivo .txt");

        JScrollPane scrollCuadroSuperior = new JScrollPane(cuadroSuperior);
        scrollCuadroSuperior.setPreferredSize(new Dimension(300, 250));
        scrollCuadroSuperior.setMaximumSize(new Dimension(300, 250));
        scrollCuadroSuperior.setMinimumSize(new Dimension(300, 250));

        JButton botonSeleccionar = new JButton("Seleccionar archivo .txt");
        botonSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSeleccionar.addActionListener(e -> cargarArchivoTxt());

        JPanel cuadroInferior = new JPanel();
        cuadroInferior.setPreferredSize(new Dimension(300, 250));
        cuadroInferior.setMaximumSize(new Dimension(300, 250));
        cuadroInferior.setMinimumSize(new Dimension(300, 250));
        cuadroInferior.setBackground(new Color(220, 230, 255));
        cuadroInferior.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel textoCuadroInferior = new JLabel("Cuadrado inferior");
        cuadroInferior.add(textoCuadroInferior);

        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(15));
        contenido.add(scrollCuadroSuperior);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(botonSeleccionar);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(cuadroInferior);

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

            try {
                String contenido = Files.readString(archivo.toPath(), StandardCharsets.UTF_8);
                cuadroSuperior.setText(contenido);
                cuadroSuperior.setCaretPosition(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo leer el archivo: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LectorTxt ventana = new LectorTxt();
            ventana.setVisible(true);
        });
    }
}
