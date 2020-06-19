package Vistas.Inicio;

import Controladores.ControladorIniciarDM;
import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class VistaDM_Usuario implements Error {

    public static String DM = "DM";
    public static String JUGADOR = "JUGADOR";

    private JPanel DM_Usuario;
    private JButton botonDM;
    private JButton botonJugador;
    private JLabel mensajeError;

    public VistaDM_Usuario(ControladorIniciarDM control) {
        this.controlador(control);
    }

    public VistaDM_Usuario() {

    }

    public JPanel getPanel() {
        return DM_Usuario;
    }

    /**
     *
     * @return Devuelve el JLabel para los mensajes de error
     */
    public JLabel getMensajeError() {
        return mensajeError;
    }

    /**
     *
     * @param mensaje mensaje de error que se quiere mostrar
     */
    public void setMensajeError(String mensaje) {
        this.mensajeError.setForeground(Color.red);
        mensajeError.setText(mensaje);

        resetError hebra = new resetError(this);
        hebra.start();
    }

    public void controlador(ActionListener ctr) {
        botonDM.addActionListener(ctr);
        botonDM.setActionCommand(DM);

        botonJugador.addActionListener(ctr);
        botonJugador.setActionCommand(JUGADOR);
    }

    @Override
    public void resetErrorMessage() {
        this.setMensajeError("");
    }
}