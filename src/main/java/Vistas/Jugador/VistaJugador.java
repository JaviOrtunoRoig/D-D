package Vistas.Jugador;

import Controladores.ControladorJugador;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class VistaJugador {
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
    private JTextArea TextArea;
    private JTextArea textArea1;

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

    public void controlador(ActionListener act) {

    }
}
