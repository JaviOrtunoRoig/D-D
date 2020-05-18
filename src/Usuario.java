
import java.sql.*;

public class Usuario {

	private String nombre, contraseña;
	
	public Usuario(String nom, String cont)
	{
		nombre = nom;
		contraseña = cont;
	}
	
	public String getnombre()
	{
		return nombre;
	}
	
	public String getcontraseña()
	{
		return contraseña;
	}
	
	

}
