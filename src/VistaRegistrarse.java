import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaRegistrarse {

    public static String ACEPTAR = "ACEPTAR";

    public JPanel Registro;
    private JTextField username;

    public JTextField getUsername() {
        return username;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JPasswordField getPasswordConfirmation() {
        return passwordConfirmation;
    }

    private JPasswordField password;
    private JPasswordField passwordConfirmation;
    private JButton buttonAceptar;

    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessageValue(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    private JLabel errorMessage;

    public VistaRegistrarse() {

    }


    public void controlador(ActionListener ctr) {
        buttonAceptar.addActionListener(ctr);
        buttonAceptar.setActionCommand(ACEPTAR);
    }
}
