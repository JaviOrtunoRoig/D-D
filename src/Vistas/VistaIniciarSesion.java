package Vistas;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaIniciarSesion {

    public static String INICIO = "INICIO";

    public JPanel IniciarSesion;
    private JTextField username;
    private JPasswordField password;
    private JButton buttonIniciarSesion;
    private JLabel errorMessage; //TODO Aqui se mostrará un mensaje de error cuando tenga q mostrarse


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
    public void setErrorMessage(String text)
    {
        errorMessage.setText(text);
    }

}
