package metodosBDD;

import java.sql.*;
import java.util.*;

public class ModificarStats {

    private Connection conn;
    private Statement stmt = null;

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials


    final String USER = "dundragons";
    final String PASS = "VengerHank";


    /**
     *
     * @param idPersonaje
     * @return
     * int:
     * vida personaje
     * -1 si no existe
     */
    public int getVida  (int idPersonaje) throws SQLException, ClassNotFoundException {



        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        int vida = -1;


        String sqlConsulta = "SELECT vida FROM Personaje WHERE idPersonaje = " + idPersonaje + ";";
        ResultSet resultado=stmt.executeQuery(sqlConsulta);
        resultado.next();
        vida=resultado.getInt("vida");
        return vida;

    }





}
