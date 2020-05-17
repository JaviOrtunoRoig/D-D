import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class Controlador implements ActionListener {

    VistaPG vista;
    Partida modelo;

    public Controlador(VistaPG vista, Partida modelo) {

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String e = actionEvent.getActionCommand();

        /*if (e.toUpperCase().equals()) {

        }*/
    }
}