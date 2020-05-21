package Principal;

import javax.swing.*;
import java.awt.*;
import Inicio.*;

public class Principal {

	public static JFrame frame;

    private static void createAndShowGUI() {

    	//Creamos la vista
		VistaPG vistaInicial = new VistaPG();

		// Creamos el modelo
		Partida cuenta = new Partida();

		// Creamos el controlador
		//ActionListener ctrInicio = new Controlador(vistaInicial, cuenta); //Lo he metdido en la propia vista

		// registramos el controlador en la vista
		//vistaInicial.controlador(ctrInicio); //Lo he metdido en la propia vista

		// Create and set up the window.
		frame = new JFrame();
		//frame.setSize(500,500);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(vistaInicial.Inicio);


		// Display the window.
		//frame.pack(); //si lo pongo no va el tamaño por defecto
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
