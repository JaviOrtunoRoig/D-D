package metodosBDD;

import java.sql.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreacionPersonaje {
    private static Connection conn;
    private static Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";

    private String rasgos;
    private int id;
    private int idPartida;
    private String nombre;
    private int experiencia;
    private int VidaCalculada = 6;
    private int TP = 9;
    private String idiomas;
    private int[] caracteristicasValores;
    private int[] modificadores;
    private String comportamiento;
    private int pesocalculado;
    private String usuario;
    private int raza;
    private Map<String, Integer> caracteristicas;

    public CreacionPersonaje() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
    }

    public CreacionPersonaje(String nom, String rsg, int[] caracteristicasArray, int raza, String usuario, Map<String, Integer> caracteristicas, int idPartida) throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();

        id = asignacionID("Personaje");

        modificadores = new int[6];
        this.raza = raza;
        this.usuario = usuario;
        this.caracteristicas = caracteristicas;
        this.idPartida = idPartida;
        nombre = nom;

        if (rsg.length() > 100){
            rasgos = rsg.substring(100);
        } else {
            rasgos = rsg;
        }

        caracteristicasValores = caracteristicasArray;
        asignacionModificadores(caracteristicasValores);

        asignacionVidaAutomatica();
        asignacionTP();

        experiencia = 0;
        pesocalculado = 0;

        asignacionComportamiento();
        asignacionIdiomas();
        insertarPersonaje();
        insertarModificadores();

        int cant = 0;
        int numAl;

        for(int i = 0; i < 3; i++){
            numAl = (int) (Math.random() * 6 + 1);
            cant = cant + numAl;
        }

        asignacionMonedas(cant);
    }

    public void insertarPersonaje() throws SQLException {
        String sqlPersonaje = "INSERT INTO Personaje VALUES (" +
            id + ", '" +
            nombre + "', '" +
            usuario + "', " +
            idPartida + ", " +
            raza +  ", " +
            experiencia + ", '" +
            rasgos + "', " +
            pesocalculado + ", '" +
            idiomas + "', " +
            TP + ", " +
            VidaCalculada + ", '" +
            comportamiento + "', " +
            VidaCalculada + ")";
        stmt.executeUpdate(sqlPersonaje);
    }

    public int getId(String nom) throws SQLException {

        Statement stmt3 = null;
        stmt3 = conn.createStatement();

        String sqlUs = "SELECT idPersonaje, Usuario FROM Personaje";
        ResultSet rsUs = stmt3.executeQuery(sqlUs);


        int idPer = 0;

        while (rsUs.next()) {
            if (nom.equals(rsUs.getString("usuario"))) {
                idPer = rsUs.getInt("idPersonaje");
            }
        }

        return idPer;
    }

    public int [] getCaracteristicas(String nom) throws SQLException {

        int [] sol;
        sol = new int [6];

        Statement stmt2 = null;
        stmt2 = conn.createStatement();

        String sql = "SELECT idPersonaje, Fuerza, Inteligencia, Sabiduria, Destreza, Constitución, Carisma FROM Características";
        ResultSet rsc = stmt2.executeQuery(sql);

        while(rsc.next()){
            if(rsc.getInt("idPersonaje") == getId(nom)){
                sol[0] = rsc.getInt("Fuerza");
                sol[1] = rsc.getInt("Inteligencia");
                sol[2] = rsc.getInt("Sabiduria");
                sol[3] = rsc.getInt("Destreza");
                sol[4] = rsc.getInt("Constitución");
                sol[5] = rsc.getInt("Carisma");
            }
        }

        rsc.close();

        return sol;
    }


    private int asignacionID(String nom) throws SQLException {

        String sql = "SELECT id" + nom + " FROM " + nom;

        ResultSet rs = stmt.executeQuery(sql);

        if(rs == null){
            return 1;
        } else {
            int cont = 1;
            while(rs.next()){
                cont++;
            }

            return cont;
        }


    }

    private void asignacionModificadores(int [] num){
        for(int i = 0; i < 6; i++){

            int mod = 0;

            if (num[i] == 3) {
                mod = -3;
            } else if (num[i] == 4 || num[i] == 5) {
                mod = -2;
            } else if (num[i] >= 6 && num[i] <= 8) {
                mod = -1;
            } else if (num[i] >= 13 && num[i] <= 15) {
                mod = 1;
            } else if (num[i] == 16 || num[i] == 17) {
                mod = 2;
            } else if (num[i] == 18) {
                mod = 3;
            }

            modificadores[i] = mod;
        }
    }

    public void asignacionVidaAutomatica() throws SQLException {
        String sql = "SELECT idRazas, DadoVida FROM Razas";
        ResultSet rs = stmt.executeQuery(sql);

        boolean listo = false;
        int vida = 0;

        while(rs.next() && !listo){
            if(rs.getInt("idRazas") == raza){
                vida = rs.getInt("DadoVida");
                listo = true;
            }
        }

        vida = (int) (Math.random() * vida + 1);

        int mod = modificadores[4];
        VidaCalculada = VidaCalculada + vida + mod;

    }

    public void asignacionTP() {


        int mod = modificadores[3] * (-1);

        TP = TP + mod;

    }

    private void asignacionComportamiento(){
        int numAl = (int) (Math.random() * 3 + 1);

        if(numAl == 1){
            comportamiento = "legal";
        } else if(numAl == 2){
            comportamiento = "neutro";
        } else if(numAl == 3){
            comportamiento = "caotico";
        }
    }

    private void asignacionIdiomas() {
        int num = caracteristicas.get("Inteligencia");

        if (num == 3) {
            idiomas = ("dificultades para leer y escribir");
        } else if (num == 4 || num == 5) {
            idiomas = ("No puede leer ni escribir");
        } else if (num >= 6 && num <= 8) {
            idiomas = ("No puede escribir palabras sencillas");
        } else if(num >= 9 && num <= 12){
            idiomas = ("Puede leer y escribir en su propia lengua");
        }else if (num >= 13 && num <= 15) {
            idiomas = ("Puede leer y escribir en su propia idioma + goblin");
        } else if (num == 16 || num == 17) {
            idiomas = ("Puede leer y escribir en su propia idioma + orco y goblin");
        } else if (num == 18) {
            idiomas = ("Puede leer y escribir en su propia idioma + orco + goblin + elfo");
        }
    }

    public void insertarModificadores() throws SQLException {

        int idC = asignacionID("Características");

        stmt = conn.createStatement();
        String sqlInsert = "INSERT INTO Características " + "VALUES (" + id + "," + idC + "," +
                caracteristicasValores[0] + "," + modificadores[0] + "," +
                caracteristicasValores[1] + "," + modificadores[1] + "," +
                caracteristicasValores[2] + "," + modificadores[2] + "," +
                caracteristicasValores[3] + "," + modificadores[3] + "," +
                caracteristicasValores[4] + "," + modificadores[4] + "," +
                caracteristicasValores[5] + "," + modificadores[5] + ")";
        stmt.executeUpdate(sqlInsert);
    }

    private void asignacionMonedas(int resMon) throws SQLException {

        resMon = resMon * 10;

        String sqlInsert = "INSERT INTO Moneda VALUES( " + id + ", " + 0 + ", " + 0 + ", " + 0 + ", " + resMon + ", " + 0 + ")";

        stmt.executeUpdate(sqlInsert);
    }

    public CreacionPersonaje(int [] car, String nom, String rsg, String idioms, int resVida, String comp, int resMon,
                             String usr, int idPartida, Map<String,Integer> cars, int raza) throws ClassNotFoundException, SQLException {

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();

        this.usuario = usr;
        this.idPartida = idPartida;
        this.caracteristicas = cars;
        this.raza = raza;

        modificadores = new int[6];
        id = asignacionID("Personaje");
        nombre = nom;
        rasgos = rsg;
        idiomas = idioms;
        comportamiento = comp;
        VidaCalculada = VidaCalculada + resVida;
        experiencia = 0;
        pesocalculado = 0;

        caracteristicasValores = car;

        asignacionModificadores(caracteristicasValores);
        insertarPersonaje();
        insertarModificadores();
        asignacionMonedas(resMon);
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
                sol[3] = getRaza(nom, rs.getInt("raza"))[0];
                sol[4] = Integer.toString(rs.getInt("TP")) ;
                sol[5] = Integer.toString(rs.getInt("VidaCalculada"));
                sol[6] = Integer.toString(rs.getInt("experiencia"));
                sol[7] = getRaza(nom, rs.getInt("raza"))[1];

                encontrado = true;
            }
        }



        return sol;
    }

    public String[] getRaza(String nom, int idRaz) throws SQLException, ClassNotFoundException {
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



    public Map<Integer, List<String>> getHabilidades (String nom) throws SQLException, ClassNotFoundException {

        Map<Integer, List<String>> sol = new HashMap<>();

        int raz = 4;

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

        System.out.println(sol);


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
            System.out.println(sol[cont]);
            cont++;


        }

        return sol;


    }







}
