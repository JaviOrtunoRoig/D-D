package Controladores;

import Vistas.Jugador.VistaJugador;
import metodosBDD.CreacionPersonaje;
import metodosBDD.Inventario;
import metodosBDD.JugadorBDD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ControladorJugador implements ActionListener  {

    int[] estadisticas;
    String usuario;

    VistaJugador vistaJugador;
    JugadorBDD jugadorBDD = new JugadorBDD();

    public ControladorJugador(VistaJugador vistaJugador, int[] estadisticas, String usuario) throws SQLException, ClassNotFoundException {
        this.usuario = usuario;
        this.estadisticas = estadisticas;
        this.vistaJugador = vistaJugador;
        this.vistaJugador.controlador(this);

        obtenerdatos();
    }

    private void obtenerdatos() throws SQLException, ClassNotFoundException {

        Inventario inventario = new Inventario();
        List<String> inventarioList = inventario.mostrarInventario(usuario);

        this.vistaJugador.setEstadisticas(estadisticas);
        this.vistaJugador.setInventario(inventarioList);
        this.vistaJugador.setDatos(jugadorBDD.getStats(usuario));
        this.vistaJugador.setHabilidades(jugadorBDD.habilidadEspecial(usuario));
        this.vistaJugador.setRasgos(jugadorBDD.getRasgos(usuario));
        this.vistaJugador.setIdiomas(jugadorBDD.getIdioma(usuario));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(VistaJugador.ACTUALIZAR)) {
            try {
                obtenerdatos();
            } catch (SQLException | ClassNotFoundException ex) {
                //TODO: Mostrar error en GUI
                ex.printStackTrace();
            }
        }
    }
}
