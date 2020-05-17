import javax.swing.*;
import java.awt.*;

public class Principal {

	static JFrame frame;

    private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame();
		//frame.setSize(500,500);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new VistaPG().Principal);


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
