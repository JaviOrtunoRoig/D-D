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
     * -1 error
     */
    public int getVida(String Usuario) throws SQLException, ClassNotFoundException {


        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        int vida = -1;


        if (existeUsuario(Usuario) == 1){
            String sqlConsulta = "SELECT vida FROM Personaje WHERE Usuario = '" + Usuario + "';";
            ResultSet resultado = stmt.executeQuery(sqlConsulta);
            resultado.next();
            vida = resultado.getInt("vida");
            return vida;

        } else {
            return -1;
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

        if(existeUsuario(Usuario) == 1){
            String sqlConsulta = String.format("UPDATE `dungeonsdragonsdb`.`Personaje` SET `VidaCalculada` = '%d', `vida` = '%d' WHERE (`idPersonaje` = '7');", newVida, newVida);
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

        if (existeUsuario(Usuario) == 1){
            String sqlConsulta = "SELECT experiencia FROM Personaje WHERE Usuario = '" + Usuario + "';";
            ResultSet resultado = stmt.executeQuery(sqlConsulta);
            resultado.next();
            experiencia = resultado.getInt("experiencia");
            return experiencia;
        }else {
            return -1;
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

        if(existeUsuario(Usuario) == 1){
            String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Personaje` SET `experiencia` = '"
                    + newXP + "' WHERE (`Usuario` = '"
                    + Usuario + "');";
            stmt.executeUpdate(sqlConsulta);
            return 1;
        }else{
            return -1;
        }
    }


    /**
     *
     * @param Usuario
     * @return int[]={cobre,plata,electrum,oro,platino} o null si el usuario no existe.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int[] getMoneda(String Usuario) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
        String DB_SCHEMA = "dungeonsdragonsdb";
        String USER = "dundragons";
        String var8 = "VengerHank";



        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com/dungeonsdragonsdb", "dundragons", "VengerHank");
        stmt = conn.createStatement();


        if(existeUsuario(Usuario)==1){

            System.out.println("Existe.");
            String sqlConsulta = "SELECT idPersonaje FROM Personaje WHERE Usuario = '" + Usuario + "';";
            ResultSet resultado=stmt.executeQuery(sqlConsulta);
            resultado.next();

            int idPersonaje = resultado.getInt("idPersonaje");

            System.out.println("id: " + idPersonaje);
            int monedas[] = new int[5];
            sqlConsulta = "SELECT * FROM Moneda WHERE idPersonaje = " + idPersonaje + ";";
            resultado=stmt.executeQuery(sqlConsulta);
            resultado.next();
            monedas[0]=resultado.getInt("CantidadCobre");
            monedas[1]=resultado.getInt("CantidadPlata");
            monedas[2]=resultado.getInt("CantidadElectum");
            monedas[3]=resultado.getInt("CantidadOro");
            monedas[4]=resultado.getInt("CantidadPlatino");
            return monedas;
        }else{
            return null;
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
    private static int existeUsuario(String Usuario) {

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
            resultado.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 2;
        }



        try {
            if (resultado.getRow() == 0) {
                return 2;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            return 2;
        }
    }
}