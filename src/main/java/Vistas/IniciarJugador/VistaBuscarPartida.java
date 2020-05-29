package Vistas.IniciarJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaBuscarPartida {

    public static String ATRAS2 = "ATRAS2";
    public static String UNIRMEPARTIDA = "UNIRMEPARTIDA";

    public JPanel BuscarPartida;
    private JLabel mensajeLabel;
    private JTextField IDField;
    private JPasswordField passwordField;
    private JLabel idPartidaLabel;
    private JLabel passwordLabel;
    private JButton botonBuscar;
    private JButton botonAtras;
    private JLabel errorMessage;

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
    }

    public void controlador(ActionListener ctr) {
        botonBuscar.addActionListener(ctr);
        botonBuscar.setActionCommand(UNIRMEPARTIDA);

        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS2);
    }
}
