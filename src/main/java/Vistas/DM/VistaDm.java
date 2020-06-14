package Vistas.DM;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VistaDm {

    public static String CERRAR = "CERRAR";

    private JPanel VistaDm;
    private JTabbedPane tabbedPane1;
    private JPanel Jugadores;
    private JPanel Mercado;
    private JButton Cerrar;
    private JButton Cerrar1;
    private JList list1;
    private JTabbedPane tabbedPane2;

    public JPanel getPanel() {
        return VistaDm;

    }

    public void controlador (ActionListener ctr) {
        Cerrar.addActionListener(ctr);
        Cerrar.setActionCommand(CERRAR);

        Cerrar1.addActionListener(ctr);
        Cerrar1.setActionCommand(CERRAR);

    }
}
