package metodosBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JugadorBDDnew {
    private static Connection conn;
    private static Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";

    private int experiencia;
    private String password;
    private int TP;
    private int vida;
    private String comportamiento;
    private String nombrePersonaje;
    private String usuario;
    private int [] caracteristicas;
    private int dadoVida;
    private String raza;
    private int idRaza;
    private String rasgo;
    private String idioma;

    public JugadorBDDnew(String us) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();

        String sql = "SELECT * FROM Personaje";
        ResultSet rs = stmt.executeQuery(sql);

        boolean encontrado = false;

        while (rs.next() && !encontrado){
            if(rs.getString("Usuario").equals(us)){
                experiencia = rs.getInt("experiencia");
                nombrePersonaje = rs.getString("Nombre");
                vida = rs.getInt("VidaCalculada");
                usuario = us;
                TP = rs.getInt("TP");
                comportamiento = rs.getString("comportamiento");
                idRaza = rs.getInt("raza");
                rasgo = rs.getString("rasgos");
                idioma = rs.getString("idiomas");

                encontrado = true;
            }
        }

        dadoVida = Integer.parseInt(razasStats()[1]);
        raza = razasStats()[0];
    }

    public JugadorBDDnew() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
    }

    public String getRasgo() {
        return rasgo;
    }

    public String getIdioma() {
        return idioma;
    }

    public String [] razasStats() throws SQLException {
        String [] sol = new String[2];

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sqlR = "SELECT idRazas, NombreRaza, DadoVida FROM Razas";
        ResultSet rs = stmtAux.executeQuery(sqlR);

        boolean listo = false;

        while(rs.next() && !listo){
            if(idRaza == rs.getInt("idRazas")){
                sol[0] = rs.getString("NombreRaza");
                sol[1] = Integer.toString(rs.getInt("DadoVida"));

                listo = true;
            }
        }

        return sol;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getTP() {
        return TP;
    }

    public int getVida() {
        return vida;
    }

    public String getComportamiento() {
        return comportamiento;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public int[] getCaracteristicas() {
        return caracteristicas;
    }

    public int getDadoVida() {
        return dadoVida;
    }

    public String getRaza() {
        return raza;
    }

    public String getPassword(String us, String correo) throws SQLException {
        String pass = null;

        Statement stmtAux = conn.createStatement();

        String sqlP = "SELECT nombre, contrasena, correo FROM Usuario";
        ResultSet rs = stmtAux.executeQuery(sqlP);

        boolean listo = false;

        while(rs.next() && !listo){
            if(rs.getString("nombre").equals(us) && rs.getString("correo").equals(correo)){
                pass = rs.getString("contrasena");

                listo = true;
            }
        }

        if(!listo){
            return Integer.toString(-1);
        }

        return pass;
    }

    public Map<Integer, List<String>> getHabilidades (String nom) throws SQLException, ClassNotFoundException {

        Map<Integer, List<String>> sol = new HashMap<>();

        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT idHabilidad, nombre, idRaza, descripcion, dadoHabilidad, requisitoDado " +
                "FROM HabilidadEspecial";
        ResultSet rs = stmtAux.executeQuery(sql);

        while(rs.next()){
            if(idRaza == rs.getInt("idRaza")){
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

    public String[] getStats(){
        String[] sol = new String[8];

        sol[0] = usuario;
        sol[1] = nombrePersonaje;
        sol[2] = comportamiento;
        sol[3] = raza;
        sol[4] = Integer.toString(TP);
        sol[5] = Integer.toString(vida);
        sol[6] = Integer.toString(experiencia);
        sol[7] = Integer.toString(dadoVida);


        return sol;
    }
}
