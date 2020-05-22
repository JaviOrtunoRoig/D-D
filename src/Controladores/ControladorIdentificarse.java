package Controladores;

import Modelos.Principal;
import Vistas.VistaDM_Usuario;
import Vistas.VistaIniciarSesion;
import Vistas.VistaPG;
import Vistas.VistaRegistrarse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControladorIdentificarse implements ActionListener {
	
	private Connection conn = null;
	private Statement stmt = null;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
	static final String DB_SCHEMA = "dungeonsdragonsdb";

	//Database credentials
	static final String USER = "dundragons";
	static final String PASS = "VengerHank";

	//Vista
	VistaRegistrarse vistaRegistrarse;
	VistaIniciarSesion vistaIniciarSesion;
	VistaDM_Usuario vistaDM_Usuario;

	public ControladorIdentificarse(VistaRegistrarse vista, VistaIniciarSesion vistaIni, VistaDM_Usuario vistaDM_Usuario) {
		this.vistaIniciarSesion = vistaIni;
		this.vistaRegistrarse = vista;
		this.vistaDM_Usuario = vistaDM_Usuario;
	}

	/**
	 *
	 * @param nombre del usuario.
	 * @param password del usuario.
	 * @param confPassword confirmación del password del usuario.
	 * @return true si se ha registrado al usuario satisfactoriamente.
	 */
	public boolean registrarse(String nombre, String password, String confPassword)	{

		vistaRegistrarse.setErrorMessageValue("Cuenta registrada con exito");

		boolean res = false;

		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

			stmt = conn.createStatement();
			
			boolean encontrado = false;
			
			String sqlConsulta = "SELECT nombre FROM Usuario";
			
			ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);
			
			 while(rsConsulta.next() && !encontrado){
				 
		         //Retrieve by column name
		         String nombreBD = rsConsulta.getString("nombre");
		         
		         if (nombreBD.equals(nombre)) {
		        	 encontrado = true;
		         }
			 }
			 
		    if (!encontrado) {
		    	String sqlInsert = "INSERT INTO Usuario " + "VALUES ('" + nombre + "','" + password + "')";

		    	if (password.equals(confPassword)) {

		    		stmt = conn.createStatement();
		    		stmt.executeUpdate(sqlInsert);
		    		res = true;
		    	} else {
		    		//JOptionPane.showMessageDialog(parentComponent, message);
		    		vistaRegistrarse.setErrorMessageValue("Las contrasenias no coinciden");
		    	}
		    }
		    else {
				vistaRegistrarse.setErrorMessageValue("Nombre ya existentes");

		    }
		} catch (SQLException e) {
			System.err.println("Error en la base de datos");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return res;
	}

	/**
	 *
	 * @param nombre del usuario que se quiere borrar.
	 * @return true si el usuario ha sido eliminado satisfactoriamnete de la base de datos.
	 */
	public boolean borrarUsuario(String nombre) {
		boolean res = false;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
			String sqlInsert = "DELETE from Usuario where nombre = '"+nombre+"'";
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlInsert);
			System.out.println("Borrado con exito");
			res = true;

		} catch (SQLException e) {
			System.err.println("Error en la base de datos");
		}
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return res;
	}

	/**
	 *
	 * @param nombre del usuario.
	 * @param password del usuario.
	 * @return true si el inicio de sesion ha sido satisfactorio.
	 */
	public boolean iniciarSesion(String nombre, String password) {

		boolean res = false;

		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
			stmt = conn.createStatement();

			boolean encontrado = false;
			String pass = null;


			String sqlConsulta = "SELECT nombre, contrasena FROM Usuario";
			ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);

			while(rsConsulta.next() && !encontrado){

				String nombreBD = rsConsulta.getString("nombre");

				if (nombreBD.equals(nombre)) {
					pass = rsConsulta.getString("contrasena");
					encontrado = true;
				}
			}

			if (encontrado) {

				if (pass.equals(password)) {

					res = true;
					this.vistaIniciarSesion.setErrorMessage("La contrasena es correcta. Bienvenido " + nombre);

				} else {
					this.vistaIniciarSesion.setErrorMessage("Error. Las contrasena introducida no coincide con la del usuario");
				}

			} else {
				this.vistaIniciarSesion.setErrorMessage("Error. El nombre del usuario no existe");
			}
		} catch (SQLException e) {
			this.vistaIniciarSesion.setErrorMessage("Error en la base de datos");
		}
		catch (Exception e) {
			this.vistaIniciarSesion.setErrorMessage("Error: " + e.getMessage());
		}
		return res;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String e = actionEvent.getActionCommand();

		boolean correcto = false;

		if (e.equals(VistaRegistrarse.ACEPTAR)) {
			String user = vistaRegistrarse.getUsername().getText();
			String passw = new String(vistaRegistrarse.getPassword().getPassword());
			String passWConfig = new String(vistaRegistrarse.getPasswordConfirmation().getPassword());
			correcto = this.registrarse(user, passw, passWConfig);

			if (correcto) {
				Principal.frame.setContentPane(new VistaPG().Inicio);
				Principal.frame.setVisible(true);
			}

		} else if (e.equals(VistaIniciarSesion.INICIO)) {
			String user = vistaIniciarSesion.getUsername().getText();
			String passw = new String(vistaIniciarSesion.getPassword().getPassword());
			correcto = this.iniciarSesion(user, passw);

			if (correcto) {
				//Principal.frame.setContentPane(new VistaPG().Inicio);
				//Principal.frame.setVisible(true);
				Principal.frame.setContentPane(new VistaDM_Usuario().DM_Usuario);
				Principal.frame.setVisible(true);
			}
		} else if (e.equals(VistaRegistrarse.VOLVER)) {
			Principal.frame.setContentPane(new VistaPG().Inicio);
			Principal.frame.setVisible(true);
		} else if (e.equals(VistaIniciarSesion.VOLVER)) {
			Principal.frame.setContentPane(new VistaPG().Inicio);
			Principal.frame.setVisible(true);
		}
	}
}
