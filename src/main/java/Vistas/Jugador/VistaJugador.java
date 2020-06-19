package Vistas.Jugador;

import Controladores.ControladorJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class VistaJugador {

    public static String ACTUALIZAR = "ACTUALIZAR";
    public static String BORRAR_PERSONAJE = "BORRAR_PERSONAJE";

    private JTabbedPane tabbedPanel;
    private JPanel panelJugador;
    private JPanel Stats;
    private JPanel Inventario;
    private JLabel fuerzaLabel;
    private JLabel inteligenciaLabel;
    private JLabel sabiduriaLabel;
    private JLabel destrezaLabel;
    private JLabel constitucionLabel;
    private JLabel carismaLabel;
    private JLabel nombreUsuarioLabel;
    private JLabel comportamientoLabel;
    private JLabel razaLabel;
    private JLabel TPLabel;
    private JLabel vidaLabel;
    private JLabel expLabel;
    private JLabel dadoVidaLabel;
    private JList<String> List;
    private JLabel usuarioNameValue;
    private JLabel personajeNameValue;
    private JLabel comportamientoValue;
    private JLabel razaValue;
    private JLabel TPValue;
    private JLabel vidaValue;
    private JLabel expValue;
    private JLabel dadoVidaValue;
    private JLabel fuerzaValue;
    private JLabel inteligenciaValue;
    private JLabel sabiduriaValue;
    private JLabel destrezaValue;
    private JLabel constitucionValue;
    private JLabel carismaValue;
    private JPanel informacionPanel;
    private JLabel idiomaLabel;
    private JTextArea textArea;
    private JTextArea textArea1;
    private JList <String> listHabilidades;
    private JButton actualizarDatosButton;
    private JButton borrarPersonajeButton;
    private JLabel errorMessage;
    private JLabel cobreLabel;
    private JLabel plataLabel;
    private JLabel electrumLabel;
    private JLabel oroLabel;
    private JLabel platinoLabel;
    private JLabel cobreValue;
    private JLabel plataValue;
    private JLabel electrumValue;
    private JLabel oroValue;
    private JLabel platinoValue;

    public JPanel getPanel() {
        return panelJugador;
    }

    public void setEstadisticas(int[] estadisticas) {

        fuerzaValue.setText(String.valueOf(estadisticas[0]));
        inteligenciaValue.setText(String.valueOf(estadisticas[1]));
        sabiduriaValue.setText(String.valueOf(estadisticas[2]));
        destrezaValue.setText(String.valueOf(estadisticas[3]));
        constitucionValue.setText(String.valueOf(estadisticas[4]));
        carismaValue.setText(String.valueOf(estadisticas[5]));
    }

    public void setInventario(List<String> inventario) {

        DefaultListModel<String> model = new DefaultListModel<>();

        for (String item : inventario) {
            model.addElement(item);
        }

        List.setModel(model);
    }

    public void setDatos(String[] stats) {

        usuarioNameValue.setText(stats[0]);
        personajeNameValue.setText(stats[1]);
        comportamientoValue.setText(stats[2]);
        razaValue.setText(stats[3]);
        TPValue.setText(stats[4]);
        vidaValue.setText(stats[5]);
        expValue.setText(stats[6]);
        dadoVidaValue.setText(stats[7]);
    }

    public void setErrorMessage(String mensaje) {
        errorMessage.setBackground(Color.red);
        errorMessage.setText(mensaje);
    }

    public void setHabilidades(String[] habilidades) {
        DefaultListModel<String> model = new DefaultListModel<>();

        for (int i = 0; i < habilidades.length; i++) {

            if (habilidades[i] != null) {
                String[] corte = habilidades[i].split(",");
                model.addElement("Nombre de habilidad: " + corte[0]);
                model.addElement("Descripcion: " + corte[1]);
                model.addElement("Dado de habilidad: " + corte[2]);
                model.addElement("Requisitos: " + corte[3]);
                model.addElement(" \n");
            }
        }

        listHabilidades.setModel(model);
    }

    public void setRasgos(String rasgos) {
        textArea1.setText(rasgos);

    }

    public void setIdiomas(String idioma) {
        textArea.setText(idioma);

    }

    public void controlador(ActionListener ctr) {
        actualizarDatosButton.addActionListener(ctr);
        actualizarDatosButton.setActionCommand(ACTUALIZAR);

        borrarPersonajeButton.addActionListener(ctr);
        borrarPersonajeButton.setActionCommand(BORRAR_PERSONAJE);
    }
}
