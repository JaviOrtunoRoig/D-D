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
	
	public void Registrarse(String nombre, String password, String confPassword)
	{

		vistaRegistrarse.setErrorMessageValue("nombre " + nombre + " pass: " + password + " passwc: " + confPassword);

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
		         
		         if (nombreBD.equals(nombre))
		         {
		        	 encontrado = true;
		         }
			 }
			 
		    if (!encontrado)
		    {
		    	String sqlInsert = "INSERT INTO Usuario " + "VALUES ('" + nombre + "','" + password + "')";

		    	
		    	if (password.equals(confPassword))
		    	{
		    		stmt = conn.createStatement();
		    		stmt.executeUpdate(sqlInsert);
		    		System.out.println("Registro realizado con exito");
		    	}
		    	else
		    	{
		    		//JOptionPane.showMessageDialog(parentComponent, message);
		    		vistaRegistrarse.setErrorMessageValue("Las contraseñas no coinciden");
		    	}
		    	
		    }
		    else
		    {
				vistaRegistrarse.setErrorMessageValue("Nombre ya existentes");

		    }
		   
			
		} catch (SQLException e) {
			
			System.err.println("Error en la base de datos");
			
			
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	

public void IniciarSesion(String nombre, String password) {
	try {

		Class.forName(JDBC_DRIVER);

		conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
		stmt = conn.createStatement();

		boolean encontrado = false;

		String sqlConsulta = "SELECT nombre, contrase?a FROM Usuario";
		ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);

		while(rsConsulta.next() && !encontrado){

			String nombreBD = rsConsulta.getString("nombre");

			if (nombreBD.equals(nombre))
			{
				encontrado = true;
			}

		}

		if(encontrado) {
			String pass = rsConsulta.getString("contrase?a");

			if(pass.equals(password)) {

				System.out.println("La contrase?a es correcta. Bienvenido " + nombre);


			} else {
				System.err.println("Error. Las contrase?a introducida no coincide con la del usuario");
			}

		} else {
			System.err.println("Error. El nombre del usuario no exite");
		}



	} catch (SQLException e) {

		System.err.println("Error en la base de datos");


	}
	catch (Exception e)
	{
		System.err.println("Error: " + e.getMessage());
	}
}



	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String e = actionEvent.getActionCommand();

		if (e.equals(VistaRegistrarse.ACEPTAR)) {
			String user = vistaRegistrarse.getUsername().getText();
			String passw = new String(vistaRegistrarse.getPassword().getPassword());
			String passWConfig = new String(vistaRegistrarse.getPasswordConfirmation().getPassword());
			this.Registrarse(user, passw, passWConfig);
		} else {
			System.out.println("df");
			String user = vistaIniciarSesion.getUsername().getText();
			String passw = new String(vistaIniciarSesion.getPassword().getPassword());
			this.IniciarSesion(user, passw);
		}
	}
}
