package Controladores;

import Vistas.Jugador.VistaJugador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ControladorJugador implements ActionListener  {

    int[] estadisticas;

    VistaJugador vistaJugador;

    public ControladorJugador(VistaJugador vistaJugador, int[] estadisticas) {
        this.estadisticas = estadisticas;
        this.vistaJugador = vistaJugador;
        this.vistaJugador.controlador(this);
        this.vistaJugador.setEstadisticas(estadisticas);
        System.out.println(Arrays.toString(estadisticas));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
