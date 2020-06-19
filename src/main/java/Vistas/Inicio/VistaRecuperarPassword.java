package Vistas.Inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaRecuperarPassword {

    public final static String VOLVER2 = "VOLVER2";
    public final static String RECUPERAR = "RECUPERAR";


    private JPanel recuperarPassword;
    private JTextField usuarioValue;
    private JLabel usuarioLabel;
    private JLabel passwordLabel;
    private JLabel passwordValue;
    private JButton encontrarPasswordButton;
    private JButton volverButton;
    private JLabel errorMessage;


    public JPanel getPanel() {
        return recuperarPassword;
    }

    public String getUsuario() {
        return usuarioValue.getText();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setForeground(Color.red);
        this.errorMessage.setText(errorMessage);
    }

    public void setPassword(String password) {
        passwordValue.setText(password);
    }

    public void controlador(ActionListener ctr) {
        volverButton.addActionListener(ctr);
        volverButton.setActionCommand(VOLVER2);

        encontrarPasswordButton.addActionListener(ctr);
        encontrarPasswordButton.setActionCommand(RECUPERAR);
    }
}
