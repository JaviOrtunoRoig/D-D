import javax.swing.*;

import java.awt.*;

public class vista extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JButton dm;
    private JButton usuario;
    private JLabel l;
    
    public vista() {

        this.setSize(500,500);
        setLayout(new FlowLayout());

        JPanel InicioRegistro = new JPanel();
        InicioRegistro.setLayout(new GridLayout(2,1));

        dm = new JButton("Iniciar Sesion");
        usuario = new JButton("Registrarse");

        dm.setPreferredSize(new Dimension(100,100));
        usuario.setPreferredSize(new Dimension(200,200));

        InicioRegistro.add(dm);
        InicioRegistro.add(usuario);


        add(InicioRegistro, BorderLayout.CENTER);
    }
}