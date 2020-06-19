package Vistas.IniciarJugador;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaPersonajeAuto_Manual implements Error {

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

        resetError hebra = new resetError(this);
        hebra.start();
    }


    public void controlador(ActionListener ctr) {
        botonManual.addActionListener(ctr);
        botonManual.setActionCommand(MANUAL);

        botonAutomatico.addActionListener(ctr);
        botonAutomatico.setActionCommand(AUTOMATICO);
    }

    @Override
    public void resetErrorMessage() {
        this.setMensajeError("");
    }
}
