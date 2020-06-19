package Vistas.Inicio;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaIniciarSesion implements Error {

    public static String INICIO = "INICIO";
    public static String VOLVER = "VOLVER";
    public static String RECUPERAR_PASSWORD = "RECUPERAR_PASSWORD";

    private JPanel IniciarSesion;
    private JTextField username;
    private JPasswordField password;
    private JButton buttonIniciarSesion;
    private JLabel errorMessage;
    private JLabel passwordLabel;
    private JButton buttonAtrasInicioSesion;
    private JLabel usuarioLabel;
    private JButton recuperarPasswordButton;

    public JPanel getPanel() {
        return IniciarSesion;
    }

    /**
     *
     * @return JTextField con el username.
     */
    public JTextField getUsername() {
        return username;
    }

    /**
     *
     * @return JPasswordField con el password.
     */
    public JPasswordField getPassword() {
        return password;
    }

    public void controlador(ActionListener ctr) {
        buttonIniciarSesion.addActionListener(ctr);
        buttonIniciarSesion.setActionCommand(INICIO);

        buttonAtrasInicioSesion.addActionListener(ctr);
        buttonAtrasInicioSesion.setActionCommand(VOLVER);

        recuperarPasswordButton.addActionListener(ctr);
        recuperarPasswordButton.setActionCommand(RECUPERAR_PASSWORD);
    }

    /**
     *
     * @return JLbael para la muestra de mensajes de error.
     */
    public JLabel getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param text Mesnaje de error que se quiere mostrar.
     */
    public void setErrorMessage(String text) {
        this.errorMessage.setForeground(Color.red);
        errorMessage.setText(text);

        resetError hebra = new resetError(this);
        hebra.start();
    }

    @Override
    public void resetErrorMessage() {
        this.setErrorMessage("");
    }
}
