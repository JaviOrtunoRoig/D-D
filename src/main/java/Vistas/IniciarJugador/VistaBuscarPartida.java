package Vistas.IniciarJugador;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaBuscarPartida implements Error {

    public static String ATRAS2 = "ATRAS2";
    public static String UNIRMEPARTIDA = "UNIRMEPARTIDA";

    private JPanel BuscarPartida;
    private JLabel mensajeLabel;
    private JTextField IDField;
    private JPasswordField passwordField;
    private JLabel idPartidaLabel;
    private JLabel passwordLabel;
    private JButton botonBuscar;
    private JButton botonAtras;
    private JLabel errorMessage;

    public JPanel getPanel() {
        return BuscarPartida;
    }

    public String getIdPartida() {
        return IDField.getText();
    }

    public String getPasswordField() {
        return new String(passwordField.getPassword());
    }

    public void setMensajeError(String error) {
        mensajeLabel.setForeground(Color.red);
        mensajeLabel.setText(error);
    }

    public void setErrorMessage(String mensaje) {
        mensajeLabel.setForeground(Color.red);
        mensajeLabel.setText(mensaje);

        resetError hebra = new resetError(this);
        hebra.start();
    }

    public void controlador(ActionListener ctr) {
        botonBuscar.addActionListener(ctr);
        botonBuscar.setActionCommand(UNIRMEPARTIDA);

        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS2);
    }

    @Override
    public void resetErrorMessage() {
        this.setMensajeError("");
    }
}
