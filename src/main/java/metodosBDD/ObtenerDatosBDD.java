package metodosBDD;

import Modelos.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase auxiliar para obtener objetos de la bbdd

public class ObtenerDatosBDD {

    private Connection conn = null;
    private Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";


    /**
     * @return Lista de objetos "Arma"
     */
    public List<Arma> getArmas()
    {
        List<Arma> lista = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT nombre, tipoDado, precio, peso FROM Armas";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String nombre = rs.getString("nombre");
                int tipoDado = rs.getInt("tipoDado");
                int precio = rs.getInt("precio");
                int peso = rs.getInt("peso");

                Arma arma = new Arma(nombre, peso, precio, tipoDado);

              lista.add(arma);
            }


        }catch(SQLException e)
        {
            System.err.println("Error en la base de datos");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }


        return lista;

    }

    /**
     * @return Lista de objetos "Armadura"
     */
    public List<Armadura> getArmaduras()
    {
        List<Armadura> lista = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT nombre, TP, precio, peso FROM Armaduras";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String nombre = rs.getString("nombre");
                int TP = rs.getInt("TP");
                int precio = rs.getInt("precio");
                int peso = rs.getInt("peso");

                Armadura armadura = new Armadura(nombre, peso, precio, TP);

                lista.add(armadura);
            }


        }catch(SQLException e)
        {
            System.err.println("Error en la base de datos");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }


        return lista;

    }

    /**
     * @return Lista de objetos "Utensilio"
     */
    public List<Utensilio> getUtensilios()
    {
        List<Utensilio> lista = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT nombre, peso, precio FROM Utensilios";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String nombre = rs.getString("nombre");
                int precio = rs.getInt("precio");
                int peso = rs.getInt("peso");

                Utensilio utensilio = new Utensilio(nombre, peso, precio);

                lista.add(utensilio);
            }


        }catch(SQLException e)
        {
            System.err.println("Error en la base de datos");
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }


        return lista;

    }


    public List<Jugador> getJugadores (String nombreDM)
    {
        List<Jugador> lista = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();

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


            String sqlConsultaJugadores = "SELECT Usuario, Nombre, vida, idPartida FROM dungeonsdragonsdb.Personaje";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sqlConsultaJugadores);

            while(rs.next())
            {
                int id = rs.getInt("idPartida");

                if(id == idPartida)
                {
                    Jugador j = new Jugador(rs.getString("Usuario"), rs.getString("Nombre"), rs.getInt("vida"));

                    lista.add(j);

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





        return lista;
    }










}
