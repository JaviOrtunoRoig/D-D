package Controladores;

import Modelos.Jugador;
import Modelos.Principal;
import Vistas.DM.VistaDm;
import Vistas.DM.VistaModificarJugador;
import metodosBDD.CreacionPersonaje;
import metodosBDD.ObtenerDatosBDD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ControladorDM implements ActionListener {

    String usuario;
    VistaDm vistaDm;
    VistaModificarJugador vistaModificarJugador;

    List<Jugador> jugadores;
    ObtenerDatosBDD obtenerDatosBDD;

    public ControladorDM(String usuario, VistaDm dm) {
        this.usuario = usuario;

        vistaDm = dm;
        vistaDm.controlador(this);

        obtenerDatosBDD = new ObtenerDatosBDD();
        jugadores = obtenerDatosBDD.getJugadores(usuario);
        vistaDm.setComboBoxs(jugadores);
        vistaDm.setListaJugadores(jugadores);

        vistaDm.setListaHerrero(obtenerDatosBDD.getArmas());
        vistaDm.setListaArmero(obtenerDatosBDD.getArmaduras());
        vistaDm.setListaTendero(obtenerDatosBDD.getUtensilios());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals(VistaDm.CERRAR)) {
            System.exit(0);

        } else if (comando.equals(VistaDm.EDITAR_PERSONAJE)) {
            Jugador jugador = vistaDm.getJugadorParaEditar(obtenerDatosBDD.getJugadores(usuario));

            CreacionPersonaje creacionPersonaje = null;
            try {
                creacionPersonaje = new CreacionPersonaje();
                vistaModificarJugador = new VistaModificarJugador();

                ControladorJugador controladorJugador = new ControladorJugador(creacionPersonaje.getCaracteristicas(jugador.getNombreUsuario()),
                        jugador.getNombreUsuario(), vistaDm, vistaModificarJugador);
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }

            Principal.frame.setContentPane(vistaModificarJugador.getPanel());
            Principal.frame.setVisible(true);
        }
    }
}
