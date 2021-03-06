package Vistas.IniciarJugador;

import Modelos.resetError;
import Vistas.Error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaFinAutomatico implements Error {

    public static String ATRAS3 = "ATRAS3";
    public static String ENTRARPARTIDAAUTOMATICO = "ENTRARPARTIDAAUTOMATICO";

    private JPanel nombreYRasgos;
    private JTextField nombreDato;
    private JLabel nombreLabel;
    private JLabel rasgosLabel;
    private JButton botonAtras;
    private JButton botonEntrar;
    private JLabel mensajeError;
    private JTextArea textArea1;

    public void setMensajeError(String mensaje) {
        this.mensajeError.setForeground(Color.red);
        this.mensajeError.setText(mensaje);

        resetError hebra = new resetError(this);
        hebra.start();
    }

    public JPanel getPanel() {
        return nombreYRasgos;
    }

    public String getNombrePersonaje() {
        return nombreDato.getText();
    }

    public String getRasgosPersonaje() {
        return textArea1.getText();
    }

    public void controlador(ActionListener ctr) {
        botonAtras.addActionListener(ctr);
        botonAtras.setActionCommand(ATRAS3);

        botonEntrar.addActionListener(ctr);
        botonEntrar.setActionCommand(ENTRARPARTIDAAUTOMATICO);
    }

    @Override
    public void resetErrorMessage() {
        this.setMensajeError("");
    }
}

