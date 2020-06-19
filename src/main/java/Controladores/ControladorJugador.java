package Controladores;

import Modelos.Principal;
import Vistas.DM.VistaDm;
import Vistas.DM.VistaModificarJugador;
import Vistas.Inicio.VistaDM_Usuario;
import Vistas.Jugador.VistaJugador;
import metodosBDD.*;

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
    ModificarStats modificarStats;

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

        Inventario inventario = null;
        inventario = new Inventario();
        List<String> inventarioList = inventario.mostrarInventario(usuario);

        JugadorBDDnew jugadorBDDnew = new JugadorBDDnew(usuario);

        this.vistaJugador.setEstadisticas(estadisticas);
        this.vistaJugador.setInventario(inventarioList);
        this.vistaJugador.setDatos(jugadorBDDnew.getStats());
        this.vistaJugador.setHabilidades(jugadorBDDnew.habilidadEspecial(usuario));
        this.vistaJugador.setRasgos(jugadorBDDnew.getRasgo());
        this.vistaJugador.setIdiomas(jugadorBDDnew.getIdioma());

    }


    private void obtenerdatosModificacion() throws SQLException, ClassNotFoundException {

        Inventario inventario = null;
        inventario = new Inventario();
        List<String> inventarioList = inventario.mostrarInventario(usuario);

        JugadorBDDnew jugadorBDDnew = new JugadorBDDnew(usuario);
        modificarStats = new ModificarStats();

        this.vistaModificarJugador.setEstadisticas(estadisticas);
        this.vistaModificarJugador.setInventario(inventarioList);
        this.vistaModificarJugador.setDatos(jugadorBDDnew.getStats());
        this.vistaModificarJugador.setHabilidades(jugadorBDDnew.habilidadEspecial(usuario));
        this.vistaModificarJugador.setRasgos(jugadorBDDnew.getRasgo());
        this.vistaModificarJugador.setIdiomas(jugadorBDDnew.getIdioma());

        if (modificarStats.getVida(usuario) != -1) {
            this.vistaModificarJugador.setHPMod(String.valueOf(modificarStats.getVida(usuario)));
            this.vistaModificarJugador.setExpMod(String.valueOf(modificarStats.getExperiencia(usuario)));
            //TOdo terminar
            //this.vistaModificarJugador.setCobre();
            //this.vistaModificarJugador.setPlata();
            //this.vistaModificarJugador.setElectrum();
            //this.vistaModificarJugador.setOro();
            //this.vistaModificarJugador.setPlatino();
        } else {
            vistaDm.setErrorUsuarioNoEncontrado("Usuario no encontrado");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        if (command.equals(VistaJugador.ACTUALIZAR)) {
            try {
                obtenerdatos();
            } catch (SQLException | ClassNotFoundException ex) {
                vistaJugador.setErrorMessage("Ha ocurrido un error. \n Por favor contacte con nosotros en:\n D&DProyecto@gmail.com");
            }
        } else if (command.equals(VistaModificarJugador.VOLVER)) {

            Principal.frame.setContentPane(vistaDm.getPanel());
            Principal.frame.setVisible(true);

        } else if (command.equals(VistaJugador.BORRAR_PERSONAJE)) {
            QueriesPersonaje queriesPersonaje = new QueriesPersonaje();
            queriesPersonaje.borrarPersonaje(usuario);

            VistaDM_Usuario vistaDM_usuario = new VistaDM_Usuario();
            ActionListener ControladorIniciarDM = new ControladorIniciarDM(usuario, vistaDM_usuario);
            vistaDM_usuario.controlador(ControladorIniciarDM);

            Principal.frame.setContentPane(vistaDM_usuario.getPanel());
            Principal.frame.setVisible(true);

        } else if (command.equals(VistaModificarJugador.HP_MENOS)) {

            int hp = Integer.parseInt(vistaModificarJugador.getHPMod());
            if (hp > 0) {
                vistaModificarJugador.setHPMod(String.valueOf(hp - 1));
            }

        } else if (command.equals(VistaModificarJugador.HP_MAS)) {

            int hp = Integer.parseInt(vistaModificarJugador.getHPMod());
            if (hp >= 0) {
                vistaModificarJugador.setHPMod(String.valueOf(hp + 1));
            }

        } else if (command.equals(VistaModificarJugador.EXP_MENOS)) {

            int exp = Integer.parseInt(vistaModificarJugador.getExpMod());
            if (exp > 0) {
                vistaModificarJugador.setExpMod(String.valueOf(exp - 1));
            }

        } else if (command.equals(VistaModificarJugador.EXP_MAS)) {

            int exp = Integer.parseInt(vistaModificarJugador.getExpMod());
            if (exp >= 0) {
                vistaModificarJugador.setExpMod(String.valueOf(exp + 1));
            }

        } else if (command.equals(VistaModificarJugador.COBRE_MENOS)) {

        } else if (command.equals(VistaModificarJugador.COBRE_MAS)) {

        } else if (command.equals(VistaModificarJugador.PLATA_MENOS)) {

        } else if (command.equals(VistaModificarJugador.PLATA_MAS)) {

        } else if (command.equals(VistaModificarJugador.ELECTRUM_MENOS)) {

        } else if (command.equals(VistaModificarJugador.ELECTRUM_MAS)) {

        } else if (command.equals(VistaModificarJugador.ORO_MENOS)) {

        } else if (command.equals(VistaModificarJugador.ORO_MAS)) {

        } else if (command.equals(VistaModificarJugador.PLATINO_MENOS)) {

        } else if (command.equals(VistaModificarJugador.PLATINO_MAS)) {

        } else if (command.equals(VistaModificarJugador.ACTUALIZAR_MONEDAS)) {
            try {

                //Actualizar mod
                modificarStats.modificarVida(usuario, Integer.parseInt(vistaModificarJugador.getHPMod()));
                modificarStats.modificarExperiencia(usuario, Integer.parseInt(vistaModificarJugador.getExpMod()));

                //Actualizar general
                vistaModificarJugador.setHP(vistaModificarJugador.getHPMod());
                vistaModificarJugador.setExp(vistaModificarJugador.getExpMod());


            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
                vistaModificarJugador.setErrorMessage("Ha ocurrido un error. \n Por favor contacte con nosotros en:\n D&DProyecto@gmail.com");
            }
        }
    }
}
