package Controladores;

import Modelos.Gmail;
import Modelos.Principal;
import Vistas.Inicio.*;
import Vistas.Jugador.VistaJugador;
import metodosBDD.JugadorBDDnew;

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
	VistaRecuperarPassword vistaRecuperarPassword;

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
	public boolean registrarse(String nombre, String password, String confPassword, String correo)	{


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
		    	String sqlInsert = String.format("INSERT INTO `dungeonsdragonsdb`.`Usuario` " +
						"(`nombre`, `contrasena`, `correo`) VALUES ('%s', '%s', '%s');", nombre, password, correo);

		    	if (password.equals(confPassword)) {

		    		stmt = conn.createStatement();
		    		stmt.executeUpdate(sqlInsert);

					res = true;
		    	} else {
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
	 * @return true si el usuario ha sido eliminado satisfactoriamente de la base de datos.
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
					this.vistaIniciarSesion.setErrorMessage("Error. La contrasena introducida no coincide con la del usuario");
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
			String correo = vistaRegistrarse.getCorreo();
			String confCorreo = vistaRegistrarse.getCorreoConf();

			if (user.equals("") || passw.equals("") || passWConfig.equals("")) {
				vistaRegistrarse.setErrorMessageValue("Hay campos sin rellenar");
			} else if (!correo.equals(confCorreo)) {
				vistaRegistrarse.setErrorMessageValue("Los correos no coinciden");
			}

			correcto = this.registrarse(user, passw, passWConfig, correo);

			if (correcto) {
				Principal.frame.setContentPane(new VistaPG().getPanel());
				Principal.frame.setVisible(true);
			}

		} else if (e.equals(VistaIniciarSesion.INICIO)) {
			String user = vistaIniciarSesion.getUsername().getText();
			String passw = new String(vistaIniciarSesion.getPassword().getPassword());
			correcto = this.iniciarSesion(user, passw);

			if (correcto) {
				ActionListener ControladorIniciarDM = new ControladorIniciarDM(user, vistaDM_Usuario);
				vistaDM_Usuario.controlador(ControladorIniciarDM);

				Principal.frame.setContentPane(vistaDM_Usuario.getPanel());
				Principal.frame.setVisible(true);
			}
		} else if (e.equals(VistaRegistrarse.VOLVER)) {
			Principal.frame.setContentPane(new VistaPG().getPanel());
			Principal.frame.setVisible(true);
		} else if (e.equals(VistaIniciarSesion.VOLVER)) {
			Principal.frame.setContentPane(new VistaPG().getPanel());
			Principal.frame.setVisible(true);

		} else if (e.equals(VistaRecuperarPassword.VOLVER2)) {

			Principal.frame.setContentPane(vistaIniciarSesion.getPanel());
			Principal.frame.setVisible(true);

		} else if (e.equals(VistaIniciarSesion.RECUPERAR_PASSWORD)) {

			vistaRecuperarPassword = new VistaRecuperarPassword();
			vistaRecuperarPassword.controlador(this);

			Principal.frame.setContentPane(vistaRecuperarPassword.getPanel());
			Principal.frame.setVisible(true);

		} else if (e.equals(VistaRecuperarPassword.RECUPERAR)) {
			try {
				JugadorBDDnew jugadorBDDnew = new JugadorBDDnew();

				String correo = vistaRecuperarPassword.getCorreo();
				String correoConf = vistaRecuperarPassword.getCorreoConf();
				String password = jugadorBDDnew.getPassword(vistaRecuperarPassword.getUsuario(), correo);

				if (password.equals("-1")) {
					vistaRecuperarPassword.setErrorMessage("El usuario no existe o ha introudcido mal los datos");
				} else {

					if (!correo.equals(correoConf)) {
						vistaRecuperarPassword.setErrorMessage("Los correos no coinciden");
					} else {
						Gmail gmail = new Gmail();
						gmail.enviarCorreo(correo, password, vistaRecuperarPassword.getUsuario());
						vistaRecuperarPassword.setErrorMessage("Se le ha enviado un correo con su password");
					}
				}
			} catch (ClassNotFoundException | SQLException ex) {
				vistaRecuperarPassword.setErrorMessage("Ha ocurrido un error. \n Por favor contacte con nosotros en:\n DDProyectoUMA@gmail.com");
			}
		}
	}
}
