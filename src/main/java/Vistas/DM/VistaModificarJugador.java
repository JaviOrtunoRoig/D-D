package Vistas.DM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class VistaModificarJugador {

    public static String VOLVER = "VOLVER";
    public static String HP_MENOS = "HP_MENOS";
    public static String HP_MAS = "HP_MAS";
    public static String EXP_MENOS = "EXP_MENOS";
    public static String EXP_MAS = "EXP_MAS";
    public static String COBRE_MENOS = "COBRE_MENOS";
    public static String COBRE_MAS = "COBRE_MAS";
    public static String PLATA_MENOS = "PLATA_MENOS";
    public static String PLATA_MAS = "PLATA_MAS";
    public static String ELECTRUM_MENOS = "ELECTRUM_MENOS";
    public static String ELECTRUM_MAS = "ELECTRUM_MAS";
    public static String ORO_MENOS = "ORO_MENOS";
    public static String ORO_MAS = "ORO_MAS";
    public static String PLATINO_MENOS = "PLATINO_MENOS";
    public static String PLATINO_MAS = "PLATINO_MAS";


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
    private JLabel HPLabel;
    private JPanel expLabelModDatos;
    private JLabel cobreLabel;
    private JLabel plataLabel;
    private JLabel electrumLabel;
    private JLabel oroLabel;
    private JLabel platinoLabel;
    private JLabel HPValue;
    private JLabel expValueModDato;
    private JLabel cobreValue;
    private JLabel plataValue;
    private JLabel electrumValue;
    private JLabel oroValue;
    private JLabel platinoValue;
    private JButton buttonHPmenos;
    private JButton buttonHPmas;
    private JButton buttonExpMenos;
    private JButton buttonCobreMenos;
    private JButton buttonPlataMenos;
    private JButton buttonElectrumMenos;
    private JButton buttonOroMenos;
    private JButton buttonPlatinoMenos;
    private JButton buttonExpMas;
    private JButton buttonCobreMas;
    private JButton buttonPlataMas;
    private JButton buttonElectrumMas;
    private JButton buttonOroMas;
    private JButton buttonPlatinoMas;
    private JLabel errorMessage;

    public JPanel getPanel() {
        return panelJugador;
    }

    public String getHP() {
        return vidaValue.getText();
    }

    public String getExp() {
        return expValue.getText();
    }

    public String getCobre() {
        return cobreValue.getText();
    }

    public String getPlata() {
        return plataValue.getText();
    }

    public String getElectrum() {
        return electrumValue.getText();
    }

    public String getOro() {
        return oroValue.getText();
    }

    public String getPlatino() {
        return platinoValue.getText();
    }

    public void setHP(String mensaje) {
        HPValue.setText(mensaje);
    }

    public void setExp(String mensaje) {
        expValueModDato.setText(mensaje);
    }

    public void setCobre(String mensaje) {
        cobreValue.setText(mensaje);
    }

    public void setPlata(String mensaje) {
        plataValue.setText(mensaje);
    }

    public void setElectrum(String mensaje) {
        electrumValue.setText(mensaje);
    }

    public void setOro(String mensaje) {
        oroValue.setText(mensaje);
    }

    public void setPlatino(String mensaje) {
        platinoValue.setText(mensaje);
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

        buttonHPmenos.addActionListener(ctr);
        buttonHPmenos.setActionCommand(HP_MENOS);

        buttonHPmas.addActionListener(ctr);
        buttonHPmas.setActionCommand(HP_MAS);

        buttonExpMenos.addActionListener(ctr);
        buttonExpMenos.setActionCommand(EXP_MENOS);

        buttonExpMas.addActionListener(ctr);
        buttonExpMas.setActionCommand(EXP_MAS);

        buttonCobreMenos.addActionListener(ctr);
        buttonCobreMenos.setActionCommand(COBRE_MENOS);

        buttonCobreMas.addActionListener(ctr);
        buttonCobreMas.setActionCommand(COBRE_MAS);

        buttonPlataMenos.addActionListener(ctr);
        buttonPlataMenos.setActionCommand(PLATA_MENOS);

        buttonPlataMas.addActionListener(ctr);
        buttonPlataMas.setActionCommand(PLATA_MAS);

        buttonElectrumMenos.addActionListener(ctr);
        buttonElectrumMenos.setActionCommand(ELECTRUM_MENOS);

        buttonElectrumMas.addActionListener(ctr);
        buttonElectrumMas.setActionCommand(ELECTRUM_MAS);

        buttonOroMenos.addActionListener(ctr);
        buttonOroMenos.setActionCommand(ORO_MENOS);

        buttonOroMas.addActionListener(ctr);
        buttonOroMas.setActionCommand(ORO_MAS);

        buttonPlatinoMenos.addActionListener(ctr);
        buttonPlatinoMenos.setActionCommand(PLATINO_MENOS);

        buttonPlatinoMas.addActionListener(ctr);
        buttonPlatinoMas.setActionCommand(PLATINO_MAS);
    }
}
