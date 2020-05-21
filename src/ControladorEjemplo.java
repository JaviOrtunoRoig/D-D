import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEjemplo implements ActionListener {

    VistaPG vistaInicial;
    Partida modelo;

    public ControladorEjemplo(VistaPG vistaInicial, Partida modelo) {
        this.vistaInicial = vistaInicial;
        this.modelo = modelo;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String e = actionEvent.getActionCommand();

        if (e.equals(VistaPG.REGISTRARSE)) {

        } else if (e.equals(VistaPG.INICIARSESION)) {
           Principal.frame.setContentPane(new VistaIniciarSesion().IniciarSesion);
           Principal.frame.setVisible(true);
        }
    }
}