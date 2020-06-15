package Vistas.DM;

import Modelos.Jugador;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaDm {

    public static String CERRAR = "CERRAR";
    public static String EDITAR_PERSONAJE = "EDITAR_PERSONAJE";

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
    private JList<String> listJugadores;
    private JList<String> listHerrero;
    private JList<String> listArmero;
    private JList<String> listTendero;

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
        for (Jugador jugador : jugadores) {
            comboBoxArmero.addItem(jugador.getNombrePersonaje());
            comboBoxHerrero.addItem(jugador.getNombrePersonaje());
            comboBoxTendero.addItem(jugador.getNombrePersonaje());
            comboBoxElegirPersonaje.addItem(jugador.getNombrePersonaje());
        }
    }

    public String getPersonajeParaEditar() {

        return comboBoxElegirPersonaje.getItemAt(comboBoxElegirPersonaje.getSelectedIndex());
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

    public void controlador (ActionListener ctr) {
        Cerrar.addActionListener(ctr);
        Cerrar.setActionCommand(CERRAR);

        editarPersonajeButton.addActionListener(ctr);
        editarPersonajeButton.setActionCommand(EDITAR_PERSONAJE);
    }

}


