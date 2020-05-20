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

    public void controlador(ActionListener ctr) {
        buttonIniciarSesion.addActionListener(ctr);
        buttonIniciarSesion.setActionCommand(INICIO);
    }

    /**
     *
     * @return JLabel para el control de rrores
     */
    public JLabel getErrorMessage()
    {
        return errorMessage;
    }

    /**
     *
     * @param text que se quiere mostrar al usuario
     */
    public void setErrorMessage(String text)
    {
        errorMessage.setText(text);
    }

}
