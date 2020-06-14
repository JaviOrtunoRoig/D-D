package Controladores;

import Vistas.DM.VistaDm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorDM implements ActionListener {

    String usuario;

    public ControladorDM(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals(VistaDm.CERRAR)) {
            System.exit(0);
        }
    }
}
