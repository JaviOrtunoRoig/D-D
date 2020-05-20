import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaRegistrarse {

    public static String ACEPTAR = "ACEPTAR";

    public JPanel Registro;
    private JTextField username;
    private JPasswordField password;
    private JPasswordField passwordConfirmation;
    private JButton buttonAceptar;
    private JLabel errorMessage;

    public JLabel getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessageValue(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    public JTextField getUsername() {
        return username;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JPasswordField getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void controlador(ActionListener ctr) {
        buttonAceptar.addActionListener(ctr);
        buttonAceptar.setActionCommand(ACEPTAR);
    }
}
