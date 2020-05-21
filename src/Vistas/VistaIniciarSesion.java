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


    public JTextField getUsername() {
        return username;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public void controlador(ActionListener ctr) {
        buttonIniciarSesion.addActionListener(ctr);
        buttonIniciarSesion.setActionCommand(INICIO);
    }

    public JLabel getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String text)
    {
        errorMessage.setText(text);
    }

}
