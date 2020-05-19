import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControladorRegistro implements ActionListener {
	
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
	VistaPG vistaInicio;

	public ControladorRegistro(VistaRegistrarse vista, VistaPG vistaInicio) {
		this.vistaRegistrarse = vista;
		this.vistaInicio = vistaInicio;
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
	
	public void IniciarSesion()
	{
		
	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String e = actionEvent.getActionCommand();

		if (e.equals(VistaRegistrarse.ACEPTAR)) {
			String user = vistaRegistrarse.getUsername().getText();
			String passw = new String(vistaRegistrarse.getPassword().getPassword());
			String passWConfig = new String(vistaRegistrarse.getPasswordConfirmation().getPassword());
			this.Registrarse(user, passw, passWConfig);
		}
	}
}
