package Vistas.IniciarDM;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaCrearPartida {

    private JPanel crear;

    public static String CREAR = "CREAR";
    public static String ATRAS = "ATRAS";

    private JPasswordField passwordfield;
    private JLabel passwordLabel;
    private JButton botonCrear;
    private JButton botonAtras;
    private JLabel errorMessage;

    public JPanel getPanel() {
        return crear;
    }

    public String getPassword() {
        return new String(passwordfield.getPassword());
    }

    public void setErrorMessage(String mensaje) {
        errorMessage.setForeground(Color.red);
        errorMessage.setText(mensaje);
    }

    public void controlador(ActionListener ctr) {
        botonCrear.addActionListener(ctr);
        botonCrear.setActionCommand(CREAR);

        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS);
    }

    public void setPasswordLabel(String mensaje) {
        this.passwordLabel.setForeground(Color.red);
        passwordLabel.setText(mensaje);
    }
}
