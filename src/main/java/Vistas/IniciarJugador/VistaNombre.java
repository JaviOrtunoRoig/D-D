package Vistas.IniciarJugador;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaNombre {

    public static String ATRAS3 = "ATRAS3";
    public static String ENTRARPARTIDA = "ENTRARPARTIDA";

    private JPanel nombreYRasgos;
    private JTextField nombreDato;
    private JTextField rasgosDatos;
    private JLabel nombreLabel;
    private JLabel rasgosLabel;
    private JButton botonAtras;
    private JButton botonEntrar;

    public void controlador(ActionListener ctr) {
        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS3);

        botonEntrar.addActionListener(ctr);
        botonEntrar.setActionCommand(ENTRARPARTIDA);
    }


}
