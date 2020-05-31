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

}
