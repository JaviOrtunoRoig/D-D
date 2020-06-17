package Controladores;

import Modelos.Principal;
import Vistas.DM.VistaDm;
import Vistas.DM.VistaModificarJugador;
import Vistas.Inicio.VistaDM_Usuario;
import Vistas.Jugador.VistaJugador;
import metodosBDD.CreacionPersonaje;
import metodosBDD.Inventario;
import metodosBDD.JugadorBDD;
import metodosBDD.QueriesPersonaje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ControladorJugador implements ActionListener  {

    int[] estadisticas;
    String usuario;

    VistaJugador vistaJugador;
    VistaDm vistaDm;
    VistaModificarJugador vistaModificarJugador;
    JugadorBDD jugadorBDD = new JugadorBDD();

    public ControladorJugador(VistaJugador vistaJugador, int[] estadisticas, String usuario) throws SQLException, ClassNotFoundException {
        this.usuario = usuario;
        this.estadisticas = estadisticas;
        this.vistaJugador = vistaJugador;
        this.vistaJugador.controlador(this);

        obtenerdatos();
    }

    public ControladorJugador(int[] estadisticas, String usuario, VistaDm vistaDm, VistaModificarJugador vistaModificarJugador) throws SQLException, ClassNotFoundException {

        this.vistaDm = vistaDm;
        this.vistaDm.controlador(this);

        this.vistaModificarJugador = vistaModificarJugador;
        this.vistaModificarJugador.controlador(this);

        this.usuario = usuario;
        this.estadisticas = estadisticas;

        obtenerdatosModificacion();
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


    private void obtenerdatosModificacion() throws SQLException, ClassNotFoundException {

        Inventario inventario = new Inventario();
        List<String> inventarioList = inventario.mostrarInventario(usuario);

        this.vistaModificarJugador.setEstadisticas(estadisticas);
        this.vistaModificarJugador.setInventario(inventarioList);
        this.vistaModificarJugador.setDatos(jugadorBDD.getStats(usuario));
        this.vistaModificarJugador.setHabilidades(jugadorBDD.habilidadEspecial(usuario));
        this.vistaModificarJugador.setRasgos(jugadorBDD.getRasgos(usuario));
        this.vistaModificarJugador.setIdiomas(jugadorBDD.getIdioma(usuario));
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
        } else if (e.getActionCommand().equals(VistaModificarJugador.VOLVER)) {

            Principal.frame.setContentPane(vistaDm.getPanel());
            Principal.frame.setVisible(true);

        } else if (e.getActionCommand().equals(VistaJugador.BORRAR_PERSONAJE)) {
            QueriesPersonaje queriesPersonaje = new QueriesPersonaje();
            queriesPersonaje.borrarPersonaje(usuario);

            VistaDM_Usuario vistaDM_usuario = new VistaDM_Usuario();
            ActionListener ControladorIniciarDM = new ControladorIniciarDM(usuario, vistaDM_usuario);
            vistaDM_usuario.controlador(ControladorIniciarDM);

            Principal.frame.setContentPane(vistaDM_usuario.DM_Usuario);
            Principal.frame.setVisible(true);
        }
    }
}
