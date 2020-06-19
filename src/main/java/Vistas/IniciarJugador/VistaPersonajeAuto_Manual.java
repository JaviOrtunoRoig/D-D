package Vistas.IniciarJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPersonajeAuto_Manual {

    public static String MANUAL = "MANUAL" ;
    public static String AUTOMATICO = "AUTOMATICO";

    private JPanel pantalla;

    private JButton botonManual;
    private JButton botonAutomatico;
    private JLabel mensajeError;

    public JPanel getPanel() {
        return pantalla;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError.setForeground(Color.red);
        this.mensajeError.setText(mensajeError);
    }


    public void controlador(ActionListener ctr) {
        botonManual.addActionListener(ctr);
        botonManual.setActionCommand(MANUAL);

        botonAutomatico.addActionListener(ctr);
        botonAutomatico.setActionCommand(AUTOMATICO);
    }

}
