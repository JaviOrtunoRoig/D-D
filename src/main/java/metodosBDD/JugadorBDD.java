package metodosBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JugadorBDD {
    private static Connection conn;
    private static Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";


    public JugadorBDD() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
    }

    public int getIdUs(String nom) throws SQLException {
        String sqlUs = "SELECT idPersonaje, Usuario FROM Personaje";
        ResultSet rsUs = stmt.executeQuery(sqlUs);

        int idPer = 0;

        while (rsUs.next()) {
            if (nom.equals(rsUs.getString("usuario"))) {
                idPer = rsUs.getInt("idPersonaje");
            }
        }

        return idPer;
    }

    public String [] getStats(String nom) throws SQLException, ClassNotFoundException {
        String [] sol = new String[8];
        sol[0] = nom;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT Nombre, Usuario, comportamiento, raza, TP, VidaCalculada, experiencia FROM Personaje";
        ResultSet rs = stmtAux.executeQuery(sql);

        boolean encontrado = false;

        while(rs.next() && !encontrado){
            if(sol[0].equals(rs.getString("Usuario"))){

                sol[1] = rs.getString("Nombre");
                sol[2] = rs.getString("comportamiento");
                sol[3] = getStatsRaza(nom, rs.getInt("raza"))[0];
                sol[4] = Integer.toString(rs.getInt("TP")) ;
                sol[5] = Integer.toString(rs.getInt("VidaCalculada"));
                sol[6] = Integer.toString(rs.getInt("experiencia"));
                sol[7] = getStatsRaza(nom, rs.getInt("raza"))[1];

                encontrado = true;
            }
        }



        return sol;
    }

    public String[] getStatsRaza(String nom, int idRaz) throws SQLException, ClassNotFoundException {
        String sol[] = new String[2];

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sqlAux = "SELECT idRazas, DadoVida, NombreRaza FROM Razas";
        ResultSet rsAux = stmtAux.executeQuery(sqlAux);

        boolean encontrado = false;

        while(rsAux.next() && !encontrado){
            if(idRaz == rsAux.getInt("idRazas")){
                sol[0] = rsAux.getString("NombreRaza");
                sol[1] = rsAux.getString("DadoVida");

                encontrado = true;
            }
        }

        return sol;
    }

    public int getRaza(String nom) throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT raza, Usuario FROM Personaje";
        ResultSet rs = stmtAux.executeQuery(sql);

        boolean encontrado = false;

        int raz = 0;

        while(rs.next() && !encontrado) {
            if (nom.equals(rs.getString("Usuario"))) {
                raz = rs.getInt("raza");
                encontrado = true;
            }
        }

        return raz;
    }


    public Map<Integer, List<String>> getHabilidades (String nom) throws SQLException, ClassNotFoundException {

        Map<Integer, List<String>> sol = new HashMap<>();

        int raz = getRaza(nom);

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT idHabilidad, nombre, idRaza, descripcion, dadoHabilidad, requisitoDado " +
                "FROM HabilidadEspecial";
        ResultSet rs = stmtAux.executeQuery(sql);

        while(rs.next()){
            if(raz == rs.getInt("idRaza")){
                List<String> hab = new ArrayList<>();

                hab.add(rs.getString("nombre"));
                hab.add(rs.getString("descripcion"));
                hab.add(rs.getString("dadoHabilidad"));
                hab.add(rs.getString("requisitoDado"));


                sol.put(rs.getInt("idHabilidad"), hab);


            }
        }
        return sol;
    }

    public String[] habilidadEspecial(String nom) throws SQLException, ClassNotFoundException {
        String [] sol = new String[20];

        int cont = 0;

        Map<Integer, List<String>> HE = getHabilidades(nom);

        for(List<String> aux : HE.values()){
            String habilidad = "";

            for(String dato : aux){
                habilidad += dato + "\t";
            }

            sol[cont] = habilidad;
            cont++;
        }

        return sol;
    }

    public String getRasgos(String nom) throws SQLException, ClassNotFoundException {
        String sol = null;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT rasgos, Usuario FROM Personaje";
        ResultSet rs = stmtAux.executeQuery(sql);

        boolean encontrado = false;

        while(rs.next() && ! encontrado){
            if(nom.equals(rs.getString("Usuario"))){
                sol = rs.getString("rasgos");
            }
        }

        return sol;
    }

    public String getIdioma(String nom) throws SQLException, ClassNotFoundException {
        String sol = null;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT idiomas, Usuario FROM Personaje";
        ResultSet rs = stmtAux.executeQuery(sql);

        boolean encontrado = false;

        while(rs.next() && ! encontrado){
            if(nom.equals(rs.getString("Usuario"))){
                sol = rs.getString("idiomas");
            }
        }

        return sol;
    }
}
