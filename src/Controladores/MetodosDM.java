package Controladores;

import java.sql.*;

public class MetodosDM {



    private Connection conn = null;
    private Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";

    /** MÉTODO "estaDMEnPartida"
     *
     * @param nom: nombre del usuario.
     * @return id de la partida si es encontrada o "-1" si no ha sido encontrada
     */

    public int estaDMEnPartida(String nom){

        int id = -1;

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

            stmt = conn.createStatement();
            boolean encontrado = false;

            String query1 = "SELECT nombre, partida FROM Usuario";
            ResultSet rsConsulta = stmt.executeQuery(query1);

            while(rsConsulta.next() && !encontrado){

                String nombreBD = rsConsulta.getString("nombre");

                if (nombreBD.equals(nom)) {
                    id = rsConsulta.getInt("partida");
                    encontrado = true;
                }
            }

            if(encontrado)
            {
                String query2 = "SELECT idPartida, DM FROM Partida";
                ResultSet rsConsulta2 = stmt.executeQuery(query2);
                boolean encontradoDM = false;

                while(rsConsulta2.next() && !encontradoDM){

                    int idPartida = rsConsulta2.getInt("idPartida");
                    String DM = rsConsulta2.getString("DM");

                    if (idPartida == id && nom.equals(DM)) {
                        encontradoDM = true;
                    }
                }

                if(!encontradoDM)
                {
                    id = -1;
                }
            }

        }catch(SQLException e)
        {
            System.err.println("Error en la conexion a la base de datos");
        }
        catch(Exception e)
        {
            System.err.println("Error : " + e.getMessage());
        }



        return id;




    }

}
