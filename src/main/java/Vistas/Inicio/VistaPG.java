package Vistas.Inicio;

import Controladores.ControladorIdentificarse;
import Modelos.Principal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPG implements ActionListener{
    public static String REGISTRARSE = "REGISTRARSE";
    public static String INICIARSESION = "INICIARSESION";

    public JPanel Inicio;
    private JButton buttonIniciSesion;
    private JButton buttonRegistrarse;

    public VistaPG() {
        this.controlador(this);
    }

    public void controlador(ActionListener ctr) {
        buttonRegistrarse.addActionListener(ctr);
        buttonRegistrarse.setActionCommand(REGISTRARSE);

        buttonIniciSesion.addActionListener(ctr);
        buttonIniciSesion.setActionCommand(INICIARSESION);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String e = actionEvent.getActionCommand();

        if (e.equals(VistaPG.REGISTRARSE)) {
            VistaRegistrarse vistaRegistrarse = new VistaRegistrarse();
            VistaIniciarSesion vistaIniciarSesion = new VistaIniciarSesion();
            VistaDM_Usuario vistaDM_Usuario = new VistaDM_Usuario(null);

            ActionListener controladorRegistro = new ControladorIdentificarse(vistaRegistrarse, vistaIniciarSesion, vistaDM_Usuario); //Lo he metido en la propia vista
            vistaRegistrarse.controlador(controladorRegistro);

            Principal.frame.setContentPane(vistaRegistrarse.Registro);
            Principal.frame.setVisible(true);

        } else if (e.equals(VistaPG.INICIARSESION)) {
            VistaRegistrarse vistaRegistrarse = new VistaRegistrarse();
            VistaIniciarSesion vistaIniciarSesion = new VistaIniciarSesion();
            VistaDM_Usuario vistaDM_Usuario = new VistaDM_Usuario(null);

            ActionListener controladorInicioSesion = new ControladorIdentificarse(vistaRegistrarse, vistaIniciarSesion, vistaDM_Usuario); //Lo he metiddo en la propia vista
            vistaIniciarSesion.controlador(controladorInicioSesion);

            Principal.frame.setContentPane(vistaIniciarSesion.IniciarSesion);
            Principal.frame.setVisible(true);
        }
    }
}
