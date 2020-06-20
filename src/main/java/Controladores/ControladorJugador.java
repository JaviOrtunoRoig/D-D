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

        modificarStats = new ModificarStats();
        int[] monedas = modificarStats.getMoneda(usuario);
        this.vistaJugador.setCobreValue(monedas[0]);
        this.vistaJugador.setPlataValue(monedas[1]);
        this.vistaJugador.setElectrumValue(monedas[2]);
        this.vistaJugador.setOroValue(monedas[3]);
    }


    private void obtenerdatosModificacion() throws SQLException, ClassNotFoundException {

        Inventario inventario = null;
        inventario = new Inventario();
        List<String> inventarioList = inventario.mostrarInventario(usuario);

        JugadorBDDnew jugadorBDDnew = new JugadorBDDnew(usuario);
        modificarStats = new ModificarStats();
        int[] monedas = modificarStats.getMoneda(usuario);

        this.vistaModificarJugador.setEstadisticas(estadisticas);
        this.vistaModificarJugador.setInventario(inventarioList);
        this.vistaModificarJugador.setDatos(jugadorBDDnew.getStats());
        this.vistaModificarJugador.setHabilidades(jugadorBDDnew.habilidadEspecial(usuario));
        this.vistaModificarJugador.setRasgos(jugadorBDDnew.getRasgo());
        this.vistaModificarJugador.setIdiomas(jugadorBDDnew.getIdioma());

        this.vistaModificarJugador.setCobreValue(monedas[0]);
        this.vistaModificarJugador.setPlataValue(monedas[1]);
        this.vistaModificarJugador.setElectrumValue(monedas[2]);
        this.vistaModificarJugador.setOroValue(monedas[3]);

        if (modificarStats.getVida(usuario) != -1) {
            String vida = String.valueOf(modificarStats.getVida(usuario));
            String exp = String.valueOf(modificarStats.getExperiencia(usuario));

            this.vistaModificarJugador.setHPMod(vida);
            this.vistaModificarJugador.setHPField(vida);
            this.vistaModificarJugador.setExpMod(exp);
            this.vistaModificarJugador.setExpField(exp);
            
            this.vistaModificarJugador.setCobreValueMod(monedas[0]);
            this.vistaModificarJugador.setCobreField(String.valueOf(monedas[0]));
            this.vistaModificarJugador.setPlataValueMod(monedas[1]);
            this.vistaModificarJugador.setPlataField(String.valueOf(monedas[1]));
            this.vistaModificarJugador.setElectrumValueMod(monedas[2]);
            this.vistaModificarJugador.setelectrumField(String.valueOf(monedas[2]));
            this.vistaModificarJugador.setOroValueMod(monedas[3]);
            this.vistaModificarJugador.setOroField(String.valueOf(monedas[3]));

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
                vistaJugador.setErrorMessage("Ha ocurrido un error. \n Por favor contacte con nosotros en:\n DDProyectoUMA@gmail.com");
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

        } else if (command.equals(VistaModificarJugador.ACTUALIZAR_MONEDAS)) {
            try {

                if (vistaModificarJugador.getHPField().equals("") || vistaModificarJugador.getExpField().equals("") || vistaModificarJugador.getCobreField().equals("") ||
                        vistaModificarJugador.getPlataField().equals("") || vistaModificarJugador.getElectrumField().equals("")
                        || vistaModificarJugador.getOroField().equals("")) {

                    vistaModificarJugador.setErrorMessage("Algun campo está vacío");

                } else {

                    int hp = Integer.parseInt(vistaModificarJugador.getHPField());
                    int exp = Integer.parseInt(vistaModificarJugador.getExpField());
                    int cobre = Integer.parseInt(vistaModificarJugador.getCobreField());
                    int plata = Integer.parseInt(vistaModificarJugador.getPlataField());
                    int electrum = Integer.parseInt(vistaModificarJugador.getElectrumField());
                    int oro = Integer.parseInt(vistaModificarJugador.getOroField());

                    if (cobre >= 100) {
                        oro += cobre / 100;
                        cobre = cobre % 100;
                    }
                    if (plata >= 10) {
                        oro += plata / 10;
                        plata = plata % 10;
                    }
                    if (electrum >= 2) {
                        oro += electrum / 2;
                        electrum = electrum % 2;
                    }

                    if (hp < 0 || exp < 0 || cobre < 0 || plata < 0 || electrum < 0 || oro < 0) {
                        throw new NumberFormatException();
                    } else {
                        //Actualizar mod
                        int error = modificarStats.modificarVida(usuario, hp);

                        if (error == 2) {
                            vistaModificarJugador.setErrorMessage("El personaje ha sido eliminado,\npor favor actualice la lista de personajes");
                        } else {

                            modificarStats.modificarExperiencia(usuario, exp);
                            modificarStats.modificarMoneda(usuario, new int[]{cobre, plata, electrum, oro});

                            //Actualizar general
                            vistaModificarJugador.setHP(String.valueOf(hp));
                            vistaModificarJugador.setHPMod(String.valueOf(hp));
                            vistaModificarJugador.setExp(String.valueOf(exp));
                            vistaModificarJugador.setExpMod(String.valueOf(exp));
                            vistaModificarJugador.setCobreValue(cobre);
                            vistaModificarJugador.setCobreValueMod(cobre);
                            vistaModificarJugador.setPlataValue(plata);
                            vistaModificarJugador.setPlataValueMod(plata);
                            vistaModificarJugador.setElectrumValue(electrum);
                            vistaModificarJugador.setElectrumValueMod(electrum);
                            vistaModificarJugador.setOroValue(oro);
                            vistaModificarJugador.setOroValueMod(oro);

                            this.vistaModificarJugador.setCobreField(String.valueOf(cobre));
                            this.vistaModificarJugador.setPlataField(String.valueOf(plata));
                            this.vistaModificarJugador.setelectrumField(String.valueOf(electrum));
                            this.vistaModificarJugador.setOroField(String.valueOf(oro));
                        }
                    }
                }

            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
                vistaModificarJugador.setErrorMessage("Ha ocurrido un error. \n Por favor contacte con nosotros en:\n DDProyectoUMA@gmail.com");

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                vistaModificarJugador.setErrorMessage("En algún campo no ha metido un número válido");
            }
        }
    }
}
