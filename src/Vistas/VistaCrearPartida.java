package Vistas;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaCrearPartida {
    public JPanel crear;

    public static String CREAR = "CREAR";

    private JPasswordField passwordfield;
    private JLabel passwordLabel;
    private JButton botonCrear;

    public String getPassword() {
        return new String(passwordfield.getPassword());
    }

    public void controlador(ActionListener ctr) {
        botonCrear.addActionListener(ctr);
        botonCrear.setActionCommand(CREAR);
    }

    public void setPasswordLabel(String mensaje) {
        passwordLabel.setText(mensaje);
    }
}
