package Vistas.Inicio;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaRegistrarse implements Error {

    public static String ACEPTAR = "ACEPTAR";
    public static String VOLVER = "VOLVER";

    private JPanel Registro;
    private JTextField username;
    private JPasswordField password;
    private JPasswordField passwordConfirmation;
    private JButton buttonAceptar;
    private JLabel errorMessage;
    private JButton buttonAtrasRegistrarse;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confPasswordLabel;
    private JTextField correoField;
    private JTextField confCorreoField;

    public JPanel getPanel() {
        return Registro;
    }

    /**
     *
     * @return JLbael para la muestra de mensajes de error.
     */
    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public String getCorreo() {
        return correoField.getText();
    }

    public String getCorreoConf() {
        return confCorreoField.getText();
    }

    /**
     *
     * @param errorMessage Mesnaje de error que se quiere mostrar.
     */
    public void setErrorMessageValue(String errorMessage) {
        this.errorMessage.setForeground(Color.red);
        this.errorMessage.setText(errorMessage);

        resetError hebra = new resetError(this);
        hebra.start();
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

    /**
     *
     * @return JPasswordField con la confirmacion de password.
     */
    public JPasswordField getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void controlador(ActionListener ctr) {
        buttonAceptar.addActionListener(ctr);
        buttonAceptar.setActionCommand(ACEPTAR);

        buttonAtrasRegistrarse.addActionListener(ctr);
        buttonAtrasRegistrarse.setActionCommand(VOLVER);
    }

    @Override
    public void resetErrorMessage() {
        this.setErrorMessageValue("");
    }
}
