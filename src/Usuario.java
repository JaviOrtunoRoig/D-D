
import java.sql.*;

public class Usuario {

	private String nombre, contrase�a;
	
	public Usuario(String nom, String cont)
	{
		nombre = nom;
		contrase�a = cont;
	}
	
	public String getnombre()
	{
		return nombre;
	}
	
	public String getcontrase�a()
	{
		return contrase�a;
	}
	
	

}
