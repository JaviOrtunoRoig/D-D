package Vistas.IniciarJugador;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaEleccionManual implements Error {

    public static String ATRAS2 = "ATRAS2";
    public static String ESTADISTICAS = "ESTADISTICAS";

    private JPanel eleccionManual;
    private JLabel fuerzaLabel;
    private JLabel InteligenciaLabel;
    private JLabel SabiduriaLabel;
    private JLabel DestrezaLabel;
    private JLabel ConstitucionLabel;
    private JLabel CarismaLabel;
    private JButton botonAtras;
    private JButton estadisticasButton;
    private JTextField fuerzaDato;
    private JTextField inteligenciaDato;
    private JTextField sabiduriaDato;
    private JTextField destrezaDatos;
    private JTextField constitucionDato;
    private JTextField carismaDato;
    private JLabel errorMessage;


    public JPanel getPanel() {
        return eleccionManual;
    }

    public int[] getCaracteristicas() {
        int[] caracteristicas = new int[6];
        caracteristicas[0] = Integer.parseInt(fuerzaDato.getText());
        caracteristicas[1] = Integer.parseInt(inteligenciaDato.getText());
        caracteristicas[2] = Integer.parseInt(sabiduriaDato.getText());
        caracteristicas[3] = Integer.parseInt(destrezaDatos.getText());
        caracteristicas[4] = Integer.parseInt(constitucionDato.getText());
        caracteristicas[5] = Integer.parseInt(carismaDato.getText());
        return caracteristicas;
    }

    public void setErrorMessage(String mensaje) {
        this.errorMessage.setForeground(Color.red);
        errorMessage.setText(mensaje);

        resetError hebra = new resetError(this);
        hebra.start();
    }

    public void controlador(ActionListener ctr) {
        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS2);

        estadisticasButton.addActionListener(ctr);
        estadisticasButton.setActionCommand(ESTADISTICAS);
    }

    @Override
    public void resetErrorMessage() {
        this.setErrorMessage("");
    }
}
