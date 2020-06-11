package Controladores;

import Vistas.Jugador.VistaJugador;
import metodosBDD.CreacionPersonaje;
import metodosBDD.Inventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ControladorJugador implements ActionListener  {

    int[] estadisticas;
    String usuario;

    VistaJugador vistaJugador;
    CreacionPersonaje creacionPersonaje = new CreacionPersonaje();

    public ControladorJugador(VistaJugador vistaJugador, int[] estadisticas, String usuario) throws SQLException, ClassNotFoundException {
        this.usuario = usuario;

        Inventario inventario = new Inventario();

        List<String> inventarioList = inventario.mostrarInventario(usuario);

        this.estadisticas = estadisticas;
        this.vistaJugador = vistaJugador;
        this.vistaJugador.controlador(this);

        this.vistaJugador.setEstadisticas(estadisticas);
        this.vistaJugador.setInventario(inventarioList);
        this.vistaJugador.setDatos(creacionPersonaje.getStats(usuario));
        this.vistaJugador.setHabilidades(creacionPersonaje.habilidadEspecial(usuario));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
