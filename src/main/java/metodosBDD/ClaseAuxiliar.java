package metodosBDD;

import java.sql.*;

public class ClaseAuxiliar {

    private static Connection conn;
    private static Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";

    /**
     @param: nombreDM: nombre del DM de la partida
     */
    public void borrarPartida(String nombreDM) {
        QueriesPersonaje qp = new QueriesPersonaje();

        try {

            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();


            //Obtenemos el id de la partida

            String sqlConsultaId = "SELECT idPartida, DM FROM dungeonsdragonsdb.Partida";

            ResultSet rs = stmt.executeQuery(sqlConsultaId);

            boolean encontrado = false;

            int idPartida = -1;
            String DM;

            while(rs.next() && !encontrado)
            {
                idPartida = rs.getInt("idPartida");
                DM = rs.getString("DM");

                if(DM.equals(nombreDM))
                {
                    encontrado = true;
                }

            }


            //Borramos todos los personajes

            String sqlConsulta = "SELECT Nombre, Usuario, idPartida FROM Personaje;";
            ResultSet rsConsulta = stmt.executeQuery(sqlConsulta);

            while(rsConsulta.next())
            {
                String nombreUsuario = rsConsulta.getString("Usuario");
                int Partida = rsConsulta.getInt("idPartida");

                if(Partida == idPartida)
                {
                    qp.borrarPersonaje(nombreUsuario);
                }


            }


            //Borramos la partida

            String sqlDeletePartida = "DELETE FROM `dungeonsdragonsdb`.`Partida` WHERE (`idPartida` = '" + idPartida + "');";
            stmt.executeUpdate(sqlDeletePartida);

            //Borramos la partida para el DM


                  String sqlUpdate =  "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = NULL WHERE (`nombre` = '"+ nombreDM + "');";
                  stmt.executeUpdate(sqlUpdate);




    } catch (SQLException e) {
        System.err.println("Error en la base de datos");
    } catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }





    }

    /**
     @param: nombreDM: nombre del DM de la partida
     @return: id de la partida si la encuentra
     @return: -1 si no encuentra la partida
     */
    public int getIdPartida(String nombreDM)
    {
        int idPartida = -1;

        try {
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();

            String sqlConsultaId = "SELECT idPartida, DM FROM dungeonsdragonsdb.Partida";

            ResultSet rs = stmt.executeQuery(sqlConsultaId);

            boolean encontrado = false;

            String DM;

            while(rs.next() && !encontrado)
            {
                idPartida = rs.getInt("idPartida");
                DM = rs.getString("DM");

                if(DM.equals(nombreDM))
                {
                    encontrado = true;
                }

            }

        }catch(SQLException e)
        {
            System.err.println("Error en la base de datos");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        return idPartida;
    }


}
