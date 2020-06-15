package Controladores;

import Modelos.Jugador;
import Modelos.Principal;
import Vistas.DM.VistaDm;
import Vistas.DM.VistaModificarJugador;
import metodosBDD.CreacionPersonaje;
import metodosBDD.Inventario;
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
    Inventario inventario;

    public ControladorDM(String usuario, VistaDm dm) {
        //TODO: tratar bien
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

        try {
            inventario = new Inventario();
        } catch (ClassNotFoundException | SQLException e) {
            //TODO tratar bien
            e.printStackTrace();
        }

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

        } else if (comando.equals(VistaDm.ADD_HERRERO)) {
            try {
                inventario.aniadirItem(vistaDm.getPersonajeHerrero(), "Herrero", vistaDm.getObjetoHerrero());
            } catch (SQLException ex) {
                //TODO: tratar bien
                ex.printStackTrace();
            }

        } else if (comando.equals(VistaDm.ADD_ARMERO)) {
            try {
                inventario.aniadirItem(vistaDm.getPersonajeArmero(), "Armero", vistaDm.getObjetoArmero());
            } catch (SQLException ex) {
                //TODO: tratar bien
                ex.printStackTrace();
            }

        } else if (comando.equals(VistaDm.ADD_TENDERO)) {
            try {
                inventario.aniadirItem(vistaDm.getPersonajeTendero(), "Tendero", vistaDm.getObjetoTendero());
            } catch (SQLException ex) {
                //TODO: tratar bien
                ex.printStackTrace();
            }
        } else if (comando.equals(VistaDm.DELETE_HERRERO)) {
            try {
                //TODO: tratar bien
                inventario.eliminarItem(vistaDm.getPersonajeHerrero(), vistaDm.getObjetoHerrero(), "Armas");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (comando.equals(VistaDm.DELETE_ARMERO)) {
            try {
                //TODO: tratar bien
                inventario.eliminarItem(vistaDm.getPersonajeArmero(), vistaDm.getObjetoArmero(), "Armaduras");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (comando.equals(VistaDm.DELETE_TENDERO)) {
            try {
                //TODO: tratar bien
                inventario.eliminarItem(vistaDm.getPersonajeTendero(), vistaDm.getObjetoTendero(), "Utensilios");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
