/*
-----------Programa 4 -----------------------------------
Version 3 de la Tabla de Transciion el AFD1 00**00
*/

package comp_T04;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AFD1_V3 extends JFrame {

    // TABLA DEL AFD
    static int[][] tabla = {
            {0, 1, -1, 0},
            {1, 2, -1, 0},
            {2, 3, 2 ,0},
            {3, 4, 2 ,0},
            {4, 5, 2 ,1},
            {5, 5, -1,1},
    };

    private JTextArea areaArchivo;
    private JTextPane areaResultado;

    public AFD1_V3() {

        setTitle("Vista de archivo TXT");
        setSize(500,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Vista de archivo TXT");
        titulo.setFont(new Font("Arial",Font.BOLD,20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));

        // AREA SUPERIOR (ARCHIVO)
        areaArchivo = new JTextArea(10,30);
        areaArchivo.setEditable(false);
        areaArchivo.setLineWrap(true);
        areaArchivo.setText("Aquí se mostrará el contenido del archivo .txt");

        JScrollPane scrollArchivo = new JScrollPane(areaArchivo);
        panel.add(scrollArchivo);

        panel.add(Box.createVerticalStrut(10));

        // BOTON
        JButton boton = new JButton("Seleccionar archivo .txt");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(boton);

        panel.add(Box.createVerticalStrut(20));

        JLabel etiqueta = new JLabel("Resultado del análisis");
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(etiqueta);

        panel.add(Box.createVerticalStrut(10));

        // AREA RESULTADOS
        areaResultado = new JTextPane();
        areaResultado.setEditable(false);

        JScrollPane scrollResultado = new JScrollPane(areaResultado);
        scrollResultado.setPreferredSize(new Dimension(400,250));

        panel.add(scrollResultado);

        add(panel);

        // ACCION DEL BOTON
        boton.addActionListener(e -> abrirArchivo());
    }

    // METODO PARA ABRIR ARCHIVO
    private void abrirArchivo(){

        JFileChooser selector = new JFileChooser();

        int opcion = selector.showOpenDialog(this);

        if(opcion == JFileChooser.APPROVE_OPTION){

            File archivo = selector.getSelectedFile();

            areaArchivo.setText("");
            areaResultado.setText("");

            try(BufferedReader br = new BufferedReader(new FileReader(archivo))){

                String linea;
                int numLinea = 1;

                while((linea = br.readLine()) != null){

                    areaArchivo.append(linea + "\n");

                    if(linea.trim().isEmpty()){

                        escribirResultado(
                                "Línea "+numLinea+" -> CADENA NO VALIDA\n",
                                new Color(200,0,0)
                        );

                        numLinea++;
                        continue;
                    }

                    boolean valida = analizarCadena(linea);

                    if(valida){

                        escribirResultado(
                                "Línea "+numLinea+": \""+linea+"\" -> CADENA ACEPTADA\n",
                                new Color(0,150,0)
                        );

                    }else{

                        escribirResultado(
                                "Línea "+numLinea+": \""+linea+"\" -> CADENA NO VALIDA\n",
                                new Color(200,0,0)
                        );

                    }

                    numLinea++;

                }

            }catch(IOException ex){

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo leer el archivo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        }

    }

    // ESCRIBIR TEXTO CON COLOR
    private void escribirResultado(String texto, Color color){

        StyledDocument doc = areaResultado.getStyledDocument();
        Style estilo = areaResultado.addStyle("estilo", null);

        StyleConstants.setForeground(estilo,color);

        try{

            doc.insertString(doc.getLength(), texto, estilo);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ANALIZADOR DE CADENA
    static boolean analizarCadena(String cadena){

        int estado = 0;
        int entrada;
        int i = 0;
        char simbolo;

        do{

            if(i < cadena.length()){
                simbolo = cadena.charAt(i);
            }else{
                simbolo = '#';
            }

            i++;

            if(simbolo == '0'){
                entrada = 1;
            }
            else if(simbolo == '1'){
                entrada = 2;
            }
            else if(simbolo == '#'){
                entrada = 0;
            }
            else{
                return false;
            }

            estado = tabla[estado][entrada];

            if(estado == -1 || estado == 0){
                return false;
            }

        }while(simbolo != '#');

        return tabla[estado][3] == 1;

    }

    // MAIN
    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {

            new AFD1_V3().setVisible(true);

        });

    }

}