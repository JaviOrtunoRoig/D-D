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
     * @param Usuario
     * @return int:
     * vida personaje
     * -1 si no existe
     * -2 error de conexion
     */
    public int getVida(String Usuario) throws SQLException, ClassNotFoundException {


        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        int vida = -1;


        if(existeUsuario(Usuario)==1){
            String sqlConsulta = "SELECT vida FROM Personaje WHERE Usuario = '" + Usuario + "';";
            ResultSet resultado = stmt.executeQuery(sqlConsulta);
            resultado.next();
            vida = resultado.getInt("vida");
            return vida;
        }else if(existeUsuario(Usuario)==2){
            return -1;
        }else{
            return -2;
        }


    }


    /**
     * @param Usuario
     * @param newVida
     * @return 1 si bien, 2 si no existe el personaje-usuario, 3 error de conexión con la BDD
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int modificarVida(String Usuario, int newVida) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com/dungeonsdragonsdb", "dundragons", "VengerHank");
        stmt = conn.createStatement();

        if(existeUsuario(Usuario)==1){
            String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Personaje` SET `vida` = '"
                    + newVida + "' WHERE (`Usuario` = '"
                    + Usuario + "');";
            stmt.executeUpdate(sqlConsulta);
            return 1;
        }else{
            return existeUsuario(Usuario);
        }


    }


    /**
     *
     * @param Usuario
     * @return experiencia personaje, -1 no existe usuario, -2 error de conexion
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int getExperiencia(String Usuario) throws SQLException, ClassNotFoundException {


        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        int experiencia = -1;

        if(existeUsuario(Usuario)==1){
            String sqlConsulta = "SELECT experiencia FROM Personaje WHERE Usuario = '" + Usuario + "';";
            ResultSet resultado = stmt.executeQuery(sqlConsulta);
            resultado.next();
            experiencia = resultado.getInt("experiencia");
            return experiencia;
        }else if(existeUsuario(Usuario)==2){
        return -1;
        }else{
        return -2;
        }


    }


    /**
     *
     * @param Usuario
     * @param newXP
     * @return 1 si bien, 2 si no existe el personaje-usuario, 3 error de conexión con la BDD
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int modificarExperiencia(String Usuario, int newXP) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com/dungeonsdragonsdb", "dundragons", "VengerHank");
        stmt = conn.createStatement();

        if(existeUsuario(Usuario)==1){
            String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Personaje` SET `experiencia` = '"
                    + newXP + "' WHERE (`Usuario` = '"
                    + Usuario + "');";
            stmt.executeUpdate(sqlConsulta);
            return 1;
        }else{
            return existeUsuario(Usuario);
        }


    }





















    /**
     *
     * @param Usuario
     * @return
     * 1 existe
     * 2 error bdd
     * 3 error conexion
     */
    private int existeUsuario(String Usuario) {

        Connection conn = null;
        Statement stmt = null;
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
        String DB_SCHEMA = "dungeonsdragonsdb";
        String USER = "dundragons";
        String var8 = "VengerHank";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com/dungeonsdragonsdb", "dundragons", "VengerHank");
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            return 3;
        } catch (SQLException e) {
            return 3;
        }

        ResultSet resultado;
        try {
            String sqlConsulta = "SELECT Nombre FROM Personaje WHERE Usuario = '" + Usuario + "';";
            resultado = stmt.executeQuery(sqlConsulta);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 2;
        }

        try {
            if (resultado.getRow() == 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            return 2;
        }
    }
}
