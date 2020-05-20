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

	//  Database credentials
	static final String USER = "dundragons";
	static final String PASS = "VengerHank";

	//Vista
	VistaRegistrarse vistaRegistrarse;
	VistaIniciarSesion vistaIniciarSesion;

	public ControladorIdentificarse(VistaRegistrarse vista, VistaIniciarSesion vistaIni) {
		this.vistaIniciarSesion = vistaIni;
		this.vistaRegistrarse = vista;
	}
	
	public boolean Registrarse(String nombre, String password, String confPassword)	{

		vistaRegistrarse.setErrorMessageValue("Cuenta registrada con exito");

		boolean res = false;

		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
			stmt = conn.createStatement();
			
			boolean encontrado = false;
			
			String sqlConsulta = "SELECT nombre FROM Usuario";
			ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);
			
			 while (rsConsulta.next() && !encontrado) {
				 
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
		    		System.out.println("Registro realizado con exito");
		    		res = true;

		    	} else {
		    		//JOptionPane.showMessageDialog(parentComponent, message);
		    		vistaRegistrarse.setErrorMessageValue("Las contraseñas no coinciden");
		    	}
		    	
		    }
		    else {
				vistaRegistrarse.setErrorMessageValue("Nombre ya existentes");
		    }

		} catch (SQLException e) {
			System.err.println("Error en la base de datos");
		}
		catch (Exception e)	{
			System.err.println("Error: " + e.getMessage());
		}

		return res;
	}
	

	public boolean IniciarSesion(String nombre, String password) {

		boolean res = false;

		try {

		Class.forName(JDBC_DRIVER);

		conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
		stmt = conn.createStatement();

		boolean encontrado = false;
		String pass = null;

		String sqlConsulta = "SELECT nombre, contrasena FROM Usuario";
		ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);

		while (rsConsulta.next() && !encontrado) {

			String nombreBD = rsConsulta.getString("nombre");

			if (nombreBD.equals(nombre)) {
				pass = rsConsulta.getString("contrasena");
				encontrado = true;
			}
		}

		if(encontrado) {

			if(pass.equals(password)) {

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
	catch (Exception e)	{
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
			correcto = this.Registrarse(user, passw, passWConfig);
			if (correcto)
			{
				Principal.frame.setContentPane(new VistaPG().Inicio);
				Principal.frame.setVisible(true);
			}

		} else {
			String user = vistaIniciarSesion.getUsername().getText();
			String passw = new String(vistaIniciarSesion.getPassword().getPassword());
			correcto = this.IniciarSesion(user, passw);
			if (correcto)
			{
				Principal.frame.setContentPane(new VistaPG().Inicio);
				Principal.frame.setVisible(true);
			}

		}
	}
}
