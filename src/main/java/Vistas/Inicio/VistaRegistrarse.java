package Vistas.Inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaRegistrarse {

    public static String ACEPTAR = "ACEPTAR";
    public static String VOLVER = "VOLVER";

    public JPanel Registro;
    private JTextField username;
    private JPasswordField password;
    private JPasswordField passwordConfirmation;
    private JButton buttonAceptar;
    private JLabel errorMessage;
    private JButton buttonAtrasRegistrarse;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confPasswordLabel;

    /**
     *
     * @return JLbael para la muestra de mensajes de error.
     */
    public JLabel getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage Mesnaje de error que se quiere mostrar.
     */
    public void setErrorMessageValue(String errorMessage) {
        this.errorMessage.setForeground(Color.red);
        this.errorMessage.setText(errorMessage);
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
}
