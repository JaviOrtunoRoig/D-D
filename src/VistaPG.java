import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaPG {
    public static String REGISTRARSE= "Registrarse";
    public static String INICIARSESION= "IniciarSesion";

    public JPanel Principal;
    private JButton buttonIniciSesion;
    private JButton buttonRegistrarse;

    public void controlador(ActionListener ctr) {
        buttonRegistrarse.addActionListener(ctr);
        buttonRegistrarse.setActionCommand(REGISTRARSE);

        buttonIniciSesion.addActionListener(ctr);
        buttonIniciSesion.setActionCommand(INICIARSESION);
    }
}
