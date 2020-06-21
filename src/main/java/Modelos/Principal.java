package Modelos;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import Vistas.Inicio.VistaPG;

public class Principal {

	public static JFrame frame;

    private static void createAndShowGUI() {

    	//Creamos la vista
		VistaPG vistaInicial = new VistaPG();

		// Creamos el modelo
		Partida cuenta = new Partida();

		// Create and set up the window.
		frame = new JFrame();
		//frame.setSize(500,500);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setTitle("Proyecto D&D Beta 1.4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(vistaInicial.getPanel());

		// Display the window.
		frame.pack(); //si lo pongo no va el tamaño por defecto
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
