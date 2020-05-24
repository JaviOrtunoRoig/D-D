package Vistas;

import javax.swing.*;
import java.awt.event.ActionListener;


public class VistaDM_Usuario {

    public static String DM = "DM";
    public static String JUGADOR = "JUGADOR";

    public JPanel DM_Usuario;
    private JButton botonDM;
    private JButton botonJugador;


    public void controlador(ActionListener ctr) {
        botonDM.addActionListener(ctr);
        botonDM.setActionCommand(DM);

        //TODO: Aun no hace nada
        botonJugador.addActionListener(ctr);
        botonJugador.setActionCommand(JUGADOR);
    }
}