package Vistas;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaConfirCreacionPartida {

    public static String NO = "NO";
    public static String SI = "SI";

    public JPanel confirmarPartida;

    private JLabel mensajeLabel;
    private JButton botonSi;
    private JButton botonNo;

    public void controlador(ActionListener ctr) {
        botonNo.addActionListener(ctr);
        botonNo.setActionCommand(NO);

        botonSi.addActionListener(ctr);
        botonSi.setActionCommand(SI);
    }
}