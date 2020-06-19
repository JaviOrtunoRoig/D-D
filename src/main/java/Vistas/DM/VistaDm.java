package Vistas.DM;

import Modelos.Jugador;
import metodosBDD.Arma;
import metodosBDD.Armadura;
import metodosBDD.Utensilio;
import org.omg.CORBA.WStringSeqHelper;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaDm {

    public static String CERRAR = "CERRAR";
    public static String EDITAR_PERSONAJE = "EDITAR_PERSONAJE";
    public static String ADD_HERRERO = "ADD_HERRERO";
    public static String ADD_ARMERO = "ADD_ARMERO";
    public static String ADD_TENDERO = "ADD_TENDERO";
    public static String DELETE_HERRERO = "DELETE_HERRERO";
    public static String DELETE_ARMERO = "DELETE_ARMERO";
    public static String DELETE_TENDERO = "DELETE_TENDERO";
    public static String DELETE_JUGADOR = "DELETE_JUGADOR";
    public static String DELETE_PARTIDA = "DELETE_PARTIDA";
    public static String ACTUALIZAR__LISTA_JUGADORES = "ACTUALIZAR__LISTA_JUGADORES";

    private JPanel VistaDm;
    private JPanel Jugadores;
    private JPanel Mercado;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JButton Cerrar;
    private JButton Cerrar1;
    private JButton editarPersonajeButton;
    private JButton addButtonTendero;
    private JButton addButtonHerrero;
    private JButton addButtonArmero;
    private JComboBox<String> comboBoxTendero;
    private JComboBox<String> comboBoxHerrero;
    private JComboBox<String> comboBoxArmero;
    private JComboBox<String> comboBoxElegirPersonaje;
    private JComboBox<String> comboBoxHerreroObjeto;
    private JComboBox<String> comboBoxArmeroObjeto;
    private JComboBox<String> comboBoxTenderoObjeto;
    private JComboBox<String> comboBoxUsuarioEliminar;
    private JList<String> listJugadores;
    private JList<String> listHerrero;
    private JList<String> listArmero;
    private JList<String> listTendero;
    private JButton deleteButtonArmero;
    private JButton deleteButtonHerrero;
    private JButton deleteButtonTendero;
    private JButton eliminarJugadorButton;
    private JButton eliminarPartidaButton;
    private JLabel idPartidaLabel;
    private JLabel idPartidaValue;
    private JButton actualizarListaJugadoresButton;
    private JLabel messageError1;
    private JLabel messageError2;
    private JLabel messageError3;

    public JPanel getPanel() {
        return VistaDm;

    }

    public void setListaJugadores(List<Jugador> jugadores) {
        DefaultListModel<String> model = new DefaultListModel<>();

        for (Jugador jugador : jugadores) {
            model.addElement("Nombre de personaje: " + jugador.getNombrePersonaje());
            model.addElement("HP: " + Integer.toString(jugador.getVida()));
            model.addElement("\n");
        }

        listJugadores.setModel(model);
    }

    public void setComboBoxs(List<Jugador> jugadores) {

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (Jugador jugador : jugadores) {
            model.addElement(jugador.getNombrePersonaje());
        }

        comboBoxArmero.setModel(model);
        comboBoxHerrero.setModel(model);
        comboBoxTendero.setModel(model);
        comboBoxElegirPersonaje.setModel(model);

        model = new DefaultComboBoxModel<>();

        for (Jugador jugador : jugadores) {
            model.addElement(jugador.getNombreUsuario());
        }

        comboBoxUsuarioEliminar.setModel(model);
    }

    public String getUsuarioBorar() {
        return comboBoxUsuarioEliminar.getItemAt(comboBoxUsuarioEliminar.getSelectedIndex());
    }

    public Jugador getJugadorParaEditar(List<Jugador> jugadores) {
        Jugador jugadorAEditar = null;

        boolean encontrado = false;
        int i = 0;
        while (i < jugadores.size() && !encontrado) {
            if (jugadores.get(i).getNombrePersonaje().equals(comboBoxElegirPersonaje.getItemAt(comboBoxElegirPersonaje.getSelectedIndex()))) {
                jugadorAEditar = jugadores.get(i);
                encontrado = true;
            }
            i++;
        }
        return jugadorAEditar;
    }

    public void setListaHerrero(List<Arma> objetos) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Arma arma : objetos) {
            comboBoxHerreroObjeto.addItem(arma.getNombre());

           model.addElement("Nombre: " + arma.getNombre());
           model.addElement("Precio: " + arma.getPrecio());
           model.addElement("Peso: " + arma.getPeso());
           model.addElement("Tipo Dado: " + arma.getTipoDado());
           model.addElement("\n");
        }

        listHerrero.setModel(model);
    }

    public void setListaArmero(List<Armadura> objetos) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Armadura armadura : objetos) {
            comboBoxArmeroObjeto.addItem(armadura.getNombre());

            model.addElement("Nombre: " + armadura.getNombre());
            model.addElement("Precio: " + armadura.getPrecio());
            model.addElement("Peso: " + armadura.getPeso());
            model.addElement("TP: " + armadura.getTP());
            model.addElement("\n");
        }

        listArmero.setModel(model);
    }

    public void setListaTendero(List<Utensilio> objetos) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Utensilio utensilio : objetos) {
            comboBoxTenderoObjeto.addItem(utensilio.getNombre());

            model.addElement("Nombre: " + utensilio.getNombre());
            model.addElement("Precio: " + utensilio.getPrecio());
            model.addElement("Peso: " + utensilio.getPeso());
            model.addElement("\n");
        }

        listTendero.setModel(model);
    }

    public String getPersonajeHerrero() {
        return comboBoxHerrero.getItemAt(comboBoxHerrero.getSelectedIndex());
    }

    public String getPersonajeArmero() {
        return comboBoxArmero.getItemAt(comboBoxArmero.getSelectedIndex());
    }

    public String getPersonajeTendero() {
        return comboBoxTendero.getItemAt(comboBoxTendero.getSelectedIndex());
    }

    public String getObjetoHerrero() {
        return comboBoxHerreroObjeto.getItemAt(comboBoxHerreroObjeto.getSelectedIndex());
    }

    public String getObjetoArmero() {
        return comboBoxArmeroObjeto.getItemAt(comboBoxArmeroObjeto.getSelectedIndex());
    }

    public String getObjetoTendero() {
        return comboBoxTenderoObjeto.getItemAt(comboBoxTenderoObjeto.getSelectedIndex());
    }

    public void setIdPartidaValue(String id) {
        this.idPartidaValue.setText(id);
    }

    public void setMessageError1(String mensaje) {
        messageError1.setText(mensaje);
    }

    public void setMessageError2(String mensaje) {
        messageError2.setText(mensaje);
    }

    public void setMessageError3(String mensaje) {
        messageError3.setText(mensaje);
    }

    public void controlador (ActionListener ctr) {
        Cerrar.addActionListener(ctr);
        Cerrar.setActionCommand(CERRAR);

        editarPersonajeButton.addActionListener(ctr);
        editarPersonajeButton.setActionCommand(EDITAR_PERSONAJE);

        addButtonHerrero.addActionListener(ctr);
        addButtonHerrero.setActionCommand(ADD_HERRERO);

        deleteButtonHerrero.addActionListener(ctr);
        deleteButtonHerrero.setActionCommand(DELETE_HERRERO);

        addButtonArmero.addActionListener(ctr);
        addButtonArmero.setActionCommand(ADD_ARMERO);

        deleteButtonArmero.addActionListener(ctr);
        deleteButtonArmero.setActionCommand(DELETE_ARMERO);

        addButtonTendero.addActionListener(ctr);
        addButtonTendero.setActionCommand(ADD_TENDERO);

        deleteButtonTendero.addActionListener(ctr);
        deleteButtonTendero.setActionCommand(DELETE_TENDERO);

        eliminarJugadorButton.addActionListener(ctr);
        eliminarJugadorButton.setActionCommand(DELETE_JUGADOR);

        eliminarPartidaButton.addActionListener(ctr);
        eliminarPartidaButton.setActionCommand(DELETE_PARTIDA);

        actualizarListaJugadoresButton.addActionListener(ctr);
        actualizarListaJugadoresButton.setActionCommand(ACTUALIZAR__LISTA_JUGADORES);
    }

}


