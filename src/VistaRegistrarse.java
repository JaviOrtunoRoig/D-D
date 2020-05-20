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

    /**
     *
     * @return TextField con la informacion del username.
     */
    public JTextField getUsername() {
        return username;
    }

    /**
     *
     * @return PasswordField con la informacion del password.
     */
    public JPasswordField getPassword() {
        return password;
    }

    /**
     *
     * @return PasswordField con la informacion del password de confirmacion.
     */
    public JPasswordField getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void controlador(ActionListener ctr) {
        buttonAceptar.addActionListener(ctr);
        buttonAceptar.setActionCommand(ACEPTAR);
    }
}
