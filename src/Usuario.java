
import java.sql.*;

public class Usuario {

	private String nombre, password;
	
	public Usuario(String nom, String cont)
	{
		nombre = nom;
		password = cont;
	}
	
	public String getnombre()
	{
		return nombre;
	}
	
	public String getcontrasena()
	{
		return password;
	}
	
	

}
