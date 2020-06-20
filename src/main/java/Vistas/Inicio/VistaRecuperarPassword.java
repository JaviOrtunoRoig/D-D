package Vistas.Inicio;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaRecuperarPassword implements Error {

    public final static String VOLVER2 = "VOLVER2";
    public final static String RECUPERAR = "RECUPERAR";


    private JPanel recuperarPassword;
    private JTextField usuarioValue;
    private JButton encontrarPasswordButton;
    private JButton volverButton;
    private JLabel errorMessage;
    private JLabel usuarioLabel;
    private JTextField correoConfValue;
    private JTextField correoValue;

    public JPanel getPanel() {
        return recuperarPassword;
    }

    public String getCorreo() {
        return correoValue.getText();
    }

    public String getCorreoConf() {
        return correoConfValue.getText();
    }

    public String getUsuario() {
        return usuarioValue.getText();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setForeground(Color.red);
        this.errorMessage.setText(errorMessage);

        resetError hebra = new resetError(this);
        hebra.start();
    }


    public void controlador(ActionListener ctr) {
        volverButton.addActionListener(ctr);
        volverButton.setActionCommand(VOLVER2);

        encontrarPasswordButton.addActionListener(ctr);
        encontrarPasswordButton.setActionCommand(RECUPERAR);
    }

    @Override
    public void resetErrorMessage() {
        this.setErrorMessage("");
    }
}
