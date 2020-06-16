package metodosBDD;

import Modelos.Stats;

import java.sql.*;

public class QueriesPersonaje {
    private static Connection conn;
    private static Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";


    public Stats obtenerStats(String idPersonaje) {
        Stats stats = null;
        try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        String sqlConsulta = "SELECT vida, experiencia, oro FROM Personaje WHERE idPersonaje == "+idPersonaje;
        ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);
        stats =new Stats(rsConsulta.getInt("vida"), rsConsulta.getInt("experiencia"), rsConsulta.getInt("oro"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return stats;
    }



    /**
     *
     * @param nombreUsuario nombre del usuario que se quiere borrar.
     * @return true si el personaje ha sido eliminado satisfactoriamnete de la base de datos.
     */
    public boolean borrarPersonaje(String nombreUsuario)
    {
        boolean res = true;

        try {

            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();

            int idPer = getIdUs(nombreUsuario);

            System.out.println(idPer);

            borrarCaracteristicas(idPer);
            borrarInventario(idPer);
            borrarMoneda(idPer);

            String sqlDelete = "DELETE from Personaje where Usuario = '"+nombreUsuario+"'";

            System.out.println(sqlDelete);

            stmt.executeUpdate(sqlDelete);





            String sqlUpdate = "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = NULL WHERE (`nombre` = '"+ nombreUsuario +"')";

            stmt.executeUpdate(sqlUpdate);

        } catch (SQLException e) {
           System.err.println("Error en la base de datos ");
           res = false;
        }
          catch (Exception e)
          {
              res = false;
              System.err.println("Error: " + e.getMessage());
          }

        return res;

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

    public void borrarCaracteristicas(int idP) throws SQLException {
        String sqlUs = "DELETE FROM `dungeonsdragonsdb`.`Características` WHERE (`idCaracterísticas` = '" + idP + "');";
        stmt.executeUpdate(sqlUs);


    }

    public void borrarMoneda(int idP) throws SQLException {
        String sqlUs = "DELETE FROM `dungeonsdragonsdb`.`Moneda` WHERE (`idPersonaje` = '"+ idP +"');";
        stmt.executeUpdate(sqlUs);


    }

    public void borrarInventario(int idP) throws SQLException {
        Statement stmtAux = null;
        stmt = conn.createStatement();

        String sqlUs = "DELETE FROM `dungeonsdragonsdb`.`Inventario` WHERE (`idPersonaje` = '" +idP +"');";
        stmt.executeUpdate(sqlUs);


    }




}
