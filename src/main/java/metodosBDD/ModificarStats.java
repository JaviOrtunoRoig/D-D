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

    public ModificarStats() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
    }


    public int getIdUs(String nom) throws SQLException {
        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sqlUs = "SELECT idPersonaje, Usuario FROM Personaje";
        ResultSet rsUs = stmtAux.executeQuery(sqlUs);

        int idPer = 0;

        while (rsUs.next()) {
            if (nom.equals(rsUs.getString("Usuario"))) {
                idPer = rsUs.getInt("idPersonaje");
            }
        }

        return idPer;
    }

    /**
     * @param Usuario
     * @return int:
     * vida personaje
     * -1 error
     */
    public int getVida(String Usuario) throws SQLException, ClassNotFoundException {


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

        if (existeUsuario(Usuario) == 1){
            int idPer = getIdUs(Usuario);
            String sqlConsulta = String.format("UPDATE `dungeonsdragonsdb`.`Personaje` SET `VidaCalculada` = '%d', `vida` = '%d' WHERE (`idPersonaje` = '%s');", newVida, newVida, idPer);
            stmt.executeUpdate(sqlConsulta);
            return 1;
        } else{
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

        int experiencia = -1;

        if (existeUsuario(Usuario) == 1){
            String sqlConsulta = "SELECT experiencia FROM Personaje WHERE Usuario = '" + Usuario + "';";
            ResultSet resultado = stmt.executeQuery(sqlConsulta);
            resultado.next();
            experiencia = resultado.getInt("experiencia");
            return experiencia;
        } else {
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
    public int[] getMoneda(String Usuario) throws SQLException, ClassNotFoundException {

        if (existeUsuario(Usuario) == 1){
            int idPersonaje = getIdUs(Usuario);

            int monedas[] = new int[5];
            String sqlConsulta = "SELECT * FROM Moneda WHERE idPersonaje = " + idPersonaje + ";";
            ResultSet resultado=stmt.executeQuery(sqlConsulta);
            resultado.next();
            monedas[0]=resultado.getInt("CantidadCobre");
            monedas[1]=resultado.getInt("CantidadPlata");
            monedas[2]=resultado.getInt("CantidadElectum");
            monedas[3]=resultado.getInt("CantidadOro");
            monedas[4]=resultado.getInt("CantidadPlatino");
            return monedas;
        } else{
            return null;
        }
    }

    /**
     *
     * @param Usuario
     * @param monedas
     * @return 1 si bien, 2 si no existe el personaje-usuario, 3 error de conexión con la BDD
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int modificarMoneda(String Usuario, int monedas[]) throws ClassNotFoundException, SQLException {

        if (existeUsuario(Usuario)==1){
            int idPersonaje = getIdUs(Usuario);

            int CantidadCobre=monedas[0],
                    CantidadPlata=monedas[1],
                    CantidadElectum=monedas[2],
                    CantidadOro=monedas[3],
                    CantidadPlatino=0;

            String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Moneda` SET `CantidadCobre` = '"
                    + CantidadCobre + "' WHERE (`idPersonaje` = '"
                    + idPersonaje + "');";
            stmt.executeUpdate(sqlConsulta);

            sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Moneda` SET `CantidadPlata` = '"
                    + CantidadPlata + "' WHERE (`idPersonaje` = '"
                    + idPersonaje + "');";
            stmt.executeUpdate(sqlConsulta);

            sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Moneda` SET `CantidadElectum` = '"
                    + CantidadElectum + "' WHERE (`idPersonaje` = '"
                    + idPersonaje + "');";
            stmt.executeUpdate(sqlConsulta);

            sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Moneda` SET `CantidadOro` = '"
                    + CantidadOro + "' WHERE (`idPersonaje` = '"
                    + idPersonaje + "');";
            stmt.executeUpdate(sqlConsulta);

            sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Moneda` SET `CantidadPlatino` = '"
                    + CantidadPlatino + "' WHERE (`idPersonaje` = '"
                    + idPersonaje + "');";
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
     * 2 no existe
     * 3 error conexion
     */
    private int existeUsuario(String Usuario) throws SQLException, ClassNotFoundException {

        ResultSet resultado;
        String sqlConsulta = "SELECT Nombre FROM Personaje WHERE Usuario = '" + Usuario + "';";
        resultado = stmt.executeQuery(sqlConsulta);
        resultado.next();

        if (resultado.getRow() == 0) {
            return 2;
        } else {
            return 1;
        }
    }
}