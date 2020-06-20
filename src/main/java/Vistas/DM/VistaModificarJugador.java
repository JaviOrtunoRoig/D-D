package Vistas.DM;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaModificarJugador implements Error {

    public static String VOLVER = "VOLVER";
    public static String ACTUALIZAR_MONEDAS = "ACTUALIZAR_MONEDAS";

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
    private JButton volverButton;
    private JLabel HPLabelMod;
    private JPanel ModDatos;
    private JLabel cobreLabelMod;
    private JLabel plataLabelMod;
    private JLabel electrumLabelMod;
    private JLabel oroLabelMod;
    private JLabel HPValueMod;
    private JLabel expValueModDato;
    private JLabel cobreValueMod;
    private JLabel plataValueMod;
    private JLabel electrumValueMod;
    private JLabel oroValueMod;
    private JLabel platinoValue;
    private JLabel errorMessage;
    private JButton actualizarMonButton;
    private JLabel expLabelMod;
    private JLabel cobreLabel;
    private JLabel plataLabel;
    private JLabel electrumLabel;
    private JLabel oroLabel;
    private JLabel cobreValue;
    private JLabel plataValue;
    private JLabel electrumValue;
    private JLabel oroValue;
    private JTextField hpField;
    private JTextField expField;
    private JTextField cobreField;
    private JTextField plataField;
    private JTextField electrumField;
    private JTextField oroField;

    public JPanel getPanel() {
        return panelJugador;
    }

    public String getHPField() {
        return hpField.getText();
    }

    public String getExpField() {
        return expField.getText();
    }

    public String getCobreField() {
        return cobreField.getText();
    }

    public String getPlataField() {
        return plataField.getText();
    }

    public String getElectrumField() {
        return electrumField.getText();
    }

    public String getOroField() {
        return oroField.getText();
    }

    public void setHPField(String mensaje) {
        hpField.setText(mensaje);
    }

    public void setExpField(String mensaje) {
        expField.setText(mensaje);
    }

    public void setCobreField(String mensaje) {
        cobreField.setText(mensaje);
    }

    public void setPlataField(String mensaje) {
        plataField.setText(mensaje);
    }

    public void setelectrumField(String mensaje) {
        electrumField.setText(mensaje);
    }

    public void setOroField(String mensaje) {
        oroField.setText(mensaje);
    }
    public String getHPMod() {
        return HPValueMod.getText();
    }

    public String getExpMod() {
        return expValueModDato.getText();
    }

    public String getHP() {
        return HPValueMod.getText();
    }

    public String getExp() {
        return expValueModDato.getText();
    }

    public String getCobreMod() {
        return cobreValueMod.getText();
    }

    public String getPlataMod() {
        return plataValueMod.getText();
    }

    public String getElectrumMod() {
        return electrumValueMod.getText();
    }

    public String getOroMod() {
        return oroValueMod.getText();
    }

    public String getPlatino() {
        return platinoValue.getText();
    }

    public void setHP(String mensaje) {
        vidaValue.setText(mensaje);
    }

    public void setExp(String mensaje) {
        expValue.setText(mensaje);
    }

    public void setCobreValue(int n) {
        cobreValue.setText(String.valueOf(n));
    }

    public void setPlataValue(int n) {
        plataValue.setText(String.valueOf(n));
    }

    public void setElectrumValue(int n) {
        electrumValue.setText(String.valueOf(n));
    }

    public void setOroValue(int n) {
        oroValue.setText(String.valueOf(n));
    }

    public void setHPMod(String mensaje) {
        HPValueMod.setText(mensaje);
    }

    public void setExpMod(String mensaje) {
        expValueModDato.setText(mensaje);
    }

    public void setCobreValueMod(int n) {
        cobreValueMod.setText(String.valueOf(n));
    }

    public void setPlataValueMod(int n) {
        plataValueMod.setText(String.valueOf(n));
    }

    public void setElectrumValueMod(int n) {
        electrumValueMod.setText(String.valueOf(n));
    }

    public void setOroValueMod(int n) {
        oroValueMod.setText(String.valueOf(n));
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
        errorMessage.setForeground(Color.red);
        errorMessage.setText(mensaje);

        resetError hebra = new resetError(this);
        hebra.start();
    }

    public void setHabilidades(String[] habilidades) {
        DefaultListModel<String> model = new DefaultListModel<>();

        for (int i = 0; i < habilidades.length; i++) {
            if (habilidades[i] != null) {
                String[] corte = habilidades[i].split(",");
                model.addElement("Nombre de habilidad: " + corte[0]);
                model.addElement("Descripcion " + corte[1]);
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
        volverButton.addActionListener(ctr);
        volverButton.setActionCommand(VOLVER);

        actualizarMonButton.addActionListener(ctr);
        actualizarMonButton.setActionCommand(ACTUALIZAR_MONEDAS);
    }

    @Override
    public void resetErrorMessage() {
        this.setErrorMessage("");
    }
}
