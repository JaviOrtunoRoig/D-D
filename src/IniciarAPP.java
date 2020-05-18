import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IniciarAPP {
	
	private Connection conn = null;
	private Statement stmt = null;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
	static final String DB_SCHEMA = "dungeonsdragonsdb";

	//  Database credentials
	static final String USER = "dundragons";
	static final String PASS = "VengerHank";
	
	public void Registrarse(String nombre, String password, String confPassword)
	{
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
			 
		    if(!encontrado)
		    {
		    	String sqlInsert = "INSERT INTO Usuario " + "VALUES ('" + nombre + "','" + password + "')";
		         System.out.println(sqlInsert);

		    	System.out.println("Introduce la confirmación de la contraseña");
		    	
		    	
		    	if (password.equals(confPassword))
		    	{
		    		stmt = conn.createStatement();
		    		stmt.executeUpdate(sqlInsert);
		    		System.out.println("Registro realizado con exito");
		    	}
		    	else
		    	{
		    		//JOptionPane.showMessageDialog(parentComponent, message);
		    		System.err.println("Error. Las contraseña no coincide con la confirmación");
		    	}
		    	
		    }
		    else
		    {
		    	System.err.println("Error. Nombre ya existente");

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
	
	
	

}
