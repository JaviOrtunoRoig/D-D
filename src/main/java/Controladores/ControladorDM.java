package Controladores;

import Modelos.Jugador;
import Modelos.Principal;
import Vistas.DM.VistaDm;
import Vistas.DM.VistaModificarJugador;
import Vistas.Inicio.VistaDM_Usuario;
import metodosBDD.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ControladorDM implements ActionListener {

    String usuario;
    VistaDm vistaDm;
    VistaModificarJugador vistaModificarJugador;
    VistaDM_Usuario vistaDM_usuario;

    List<Jugador> jugadores;
    ObtenerDatosBDD obtenerDatosBDD;
    ClaseAuxiliar claseAuxiliar;
    Inventario inventario;

    public ControladorDM(String usuario, VistaDm dm) {
        this.usuario = usuario;

        vistaDm = dm;
        vistaDm.controlador(this);

        obtenerDatosBDD = new ObtenerDatosBDD();
        claseAuxiliar = new ClaseAuxiliar();
        jugadores = obtenerDatosBDD.getJugadores(usuario);

        vistaDm.setIdPartidaValue(String.valueOf(claseAuxiliar.getIdPartida(usuario)));

        vistaDm.setComboBoxs(jugadores);
        vistaDm.setListaJugadores(jugadores);
        vistaDm.setListaHerrero(obtenerDatosBDD.getArmas());
        vistaDm.setListaArmero(obtenerDatosBDD.getArmaduras());
        vistaDm.setListaTendero(obtenerDatosBDD.getUtensilios());

        try {
            inventario = new Inventario();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        String error = null;

        if (comando.equals(VistaDm.EDITAR_PERSONAJE)) {
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
                error = inventario.aniadirItem(vistaDm.getPersonajeHerrero(), "Herrero", vistaDm.getObjetoHerrero());
                vistaDm.setMessageError1(error);
            } catch (SQLException ex) {
                vistaDm.setMessageError1("Ha ocurrido un error. \n" +
                        " Por favor contacte con nosotros en:\n" +
                        " D&DProyecto@gmail.com");
            }

        } else if (comando.equals(VistaDm.ADD_ARMERO)) {
            try {
                error = inventario.aniadirItem(vistaDm.getPersonajeArmero(), "Armero", vistaDm.getObjetoArmero());
                vistaDm.setMessageError2(error);
            } catch (SQLException ex) {
                vistaDm.setMessageError2("Ha ocurrido un error. \n" +
                        " Por favor contacte con nosotros en:\n" +
                        " D&DProyecto@gmail.com");
            }

        } else if (comando.equals(VistaDm.ADD_TENDERO)) {
            try {
                error = inventario.aniadirItem(vistaDm.getPersonajeTendero(), "Tendero", vistaDm.getObjetoTendero());
                vistaDm.setMessageError3(error);
            } catch (SQLException ex) {
                vistaDm.setMessageError3("Ha ocurrido un error. \n" +
                        " Por favor contacte con nosotros en:\n" +
                        " D&DProyecto@gmail.com");
            }
        } else if (comando.equals(VistaDm.DELETE_HERRERO)) {
            try {
                error = inventario.eliminarItem(vistaDm.getPersonajeHerrero(), vistaDm.getObjetoHerrero(), "Armas");
                vistaDm.setMessageError1(error);
            } catch (SQLException ex) {
                vistaDm.setMessageError1("Ha ocurrido un error. \n" +
                        " Por favor contacte con nosotros en:\n" +
                        " D&DProyecto@gmail.com");
            }
        } else if (comando.equals(VistaDm.DELETE_ARMERO)) {
            try {
                error = inventario.eliminarItem(vistaDm.getPersonajeArmero(), vistaDm.getObjetoArmero(), "Armaduras");
                vistaDm.setMessageError2(error);
            } catch (SQLException ex) {
                vistaDm.setMessageError2("Ha ocurrido un error. \n" +
                        " Por favor contacte con nosotros en:\n" +
                        " D&DProyecto@gmail.com");
            }

        } else if (comando.equals(VistaDm.DELETE_TENDERO)) {
            try {
                error = inventario.eliminarItem(vistaDm.getPersonajeTendero(), vistaDm.getObjetoTendero(), "Utensilios");
                vistaDm.setMessageError3(error);
            } catch (SQLException ex) {
                vistaDm.setMessageError3("Ha ocurrido un error. \n" +
                        " Por favor contacte con nosotros en:\n" +
                        " D&DProyecto@gmail.com");
            }
        } else if (comando.equals(VistaDm.DELETE_JUGADOR)) {
            QueriesPersonaje queriesPersonaje = new QueriesPersonaje();
            queriesPersonaje.borrarPersonaje(vistaDm.getUsuarioBorar());

            vistaDm.setComboBoxs(obtenerDatosBDD.getJugadores(usuario));
            vistaDm.setListaJugadores(obtenerDatosBDD.getJugadores(usuario));

        } else if (comando.equals(VistaDm.DELETE_PARTIDA)) {
            vistaDM_usuario = new VistaDM_Usuario();
            claseAuxiliar.borrarPartida(usuario);
            ActionListener ControladorIniciarDM = new ControladorIniciarDM(usuario, vistaDM_usuario);
            vistaDM_usuario.controlador(ControladorIniciarDM);

            Principal.frame.setContentPane(vistaDM_usuario.getPanel());
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaDm.ACTUALIZAR__LISTA_JUGADORES)) {
            vistaDm.setComboBoxs(obtenerDatosBDD.getJugadores(usuario));
            vistaDm.setListaJugadores(obtenerDatosBDD.getJugadores(usuario));
        }
    }
}
