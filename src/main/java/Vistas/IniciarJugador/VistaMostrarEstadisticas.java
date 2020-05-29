package Vistas.IniciarJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaMostrarEstadisticas {

    public static String ELEGIRRAZA = "ELEGIRRAZA";
    public static String ATRAS = "ATRAS";

    private JPanel verEstaditicas;
    private JLabel fuerzaLabel;
    private JLabel InteligenciaLabel;
    private JLabel SabiduriaLabel;
    private JLabel DestrezaLabel;
    private JLabel ConstitucionLabel;
    private JLabel CarismaLabel;
    private JLabel fuerzaDatosLabel;
    private JLabel inteligenciaDatosLabel;
    private JLabel sabiduriaDatosLabel;
    private JLabel destrezaDatosLabel;
    private JLabel constitucionDatosLabel;
    private JLabel carismaDatosLabel;
    private JButton botonVolverAtras;
    private JButton botonElegirRaza;

    public VistaMostrarEstadisticas(int fuerza, int inteligencia, int sabiduria, int destreza, int constitucion, int carisma) {
        this.fuerzaDatosLabel.setText(String.valueOf(fuerza));
        this.inteligenciaDatosLabel.setText(String.valueOf(inteligencia));
        this.sabiduriaDatosLabel.setText(String.valueOf(sabiduria));
        this.destrezaDatosLabel.setText(String.valueOf(destreza));
        this.constitucionDatosLabel.setText(String.valueOf(constitucion));
        this.carismaDatosLabel.setText(String.valueOf(carisma));
    }


    public JPanel getPanel() {
        return verEstaditicas;
    }

    public void controlador(ActionListener ctr) {
        botonElegirRaza.addActionListener(ctr);
        botonElegirRaza.setActionCommand(ELEGIRRAZA);

        botonVolverAtras.addActionListener(ctr);
        botonVolverAtras.setActionCommand(ATRAS);
    }
}
