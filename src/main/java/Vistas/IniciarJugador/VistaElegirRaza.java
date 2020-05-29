package Vistas.IniciarJugador;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaElegirRaza {

    public static String GUERRERO = "GUERRERO";
    public static String ELFO = "ELFO";
    public static String ENANO = "ENANO";
    public static String LADRON = "LADRON";
    public static String HOBBIT = "HOBBIT";
    public static String CLERIGO = "CLERIGO";
    public static String MAGO = "MAGO";
    public static String ATRAS4 = "ATRAS4";

    private JPanel elegirClase;
    private JButton botonGuerrero;
    private JButton botonElfo;
    private JButton botonEnano;
    private JButton botonLadron;
    private JButton botonClerigo;
    private JButton botonHobbit;
    private JButton botonMago;
    private JButton botonAtras;

    public JPanel getPanel() {
        return elegirClase;
    }

    public void controlador(ActionListener ctr) {
        botonGuerrero.addActionListener(ctr);
        botonGuerrero.setActionCommand(GUERRERO);

        botonElfo.addActionListener(ctr);
        botonElfo.setActionCommand(ELFO);

        botonEnano.addActionListener(ctr);
        botonEnano.setActionCommand(ENANO);

        botonLadron.addActionListener(ctr);
        botonLadron.setActionCommand(LADRON);

        botonHobbit.addActionListener(ctr);
        botonHobbit.setActionCommand(HOBBIT);

        botonClerigo.addActionListener(ctr);
        botonClerigo.setActionCommand(CLERIGO);

        botonMago.addActionListener(ctr);
        botonMago.setActionCommand(MAGO);

        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS4);
    }

    public void visualizarBotones(boolean[] sePuede) {
        botonGuerrero.setEnabled(sePuede[0]);
        botonElfo.setEnabled(sePuede[1]);
        botonEnano.setEnabled(sePuede[2]);
        botonLadron.setEnabled(sePuede[3]);
        botonHobbit.setEnabled(sePuede[4]);
        botonClerigo.setEnabled(sePuede[5]);
        botonMago.setEnabled(sePuede[6]);
    }
}
