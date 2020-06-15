package Vistas.DM;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaModificarJugador {

    public static String VOLVER = "VOLVER";

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

    public void setHabilidades(String[] habilidades) {
        DefaultListModel<String> model = new DefaultListModel<>();

        for (int i = 0; i < habilidades.length; i++) {
            model.addElement(habilidades[i]);
            model.addElement(" \n");
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
    }
}
