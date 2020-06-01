package Vistas.IniciarJugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaFinManual {

    public static String ATRAS4 = "ATRAS4";
    public static String ENTRARPARTIDAMANUAL = "ENTRARPARTIDAMANUAL";

    private JPanel finManual;
    private JTextField nombreDato;
    private JTextField monedaDato;
    private JTextField comportamientoDato;
    private JTextArea idiomasDato;
    private JTextArea rasgosDatos;
    private JTextField vidaDado;
    private JButton botonAtras;
    private JButton botonEntrarPartidaManual;
    private JLabel errorMessage;
    private JComboBox comportaminetoCombo;


    public JPanel getPanel() {
        return finManual;
    }

    public void setErrorMessage(String mensaje) {
        errorMessage.setForeground(Color.red);
        errorMessage.setText(mensaje);
    }

    public String getNombre() {
        return nombreDato.getText();
    }

    public String getMoneda() {
        return monedaDato.getText();
    }

    public int getComportamientoIndex() {
        return comportaminetoCombo.getSelectedIndex();
    }

    public String getIdioma() {
        return idiomasDato.getText();
    }

    public String getRasgo() {
        return rasgosDatos.getText();
    }

    public String getVidaDado() {
        return vidaDado.getText();
    }


    public void controlador(ActionListener ctr) {
        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS4);

        botonEntrarPartidaManual.addActionListener(ctr);
        botonEntrarPartidaManual.setActionCommand(ENTRARPARTIDAMANUAL);
    }
}
