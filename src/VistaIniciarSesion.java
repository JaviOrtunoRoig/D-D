import javax.swing.*;

public class VistaIniciarSesion {
    public JPanel IniciarSesion;
    private JTextField username;
    private JPasswordField password;
    private JButton ButtonIniciarSesion;
    private JLabel errorMessage; //TODO Aqui se mostrará un mensaje de error cuando tenga q mostrarse

    public VistaIniciarSesion() {
        errorMessage.setText("");
    }
}
