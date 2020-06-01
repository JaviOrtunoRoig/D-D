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

        int mon = asignacionID("Moneda");

        String sqlInsert = "INSERT INTO Moneda VALUES( " +
                mon + ", " + id + ", " + 0 + ", " + 0 + ", " + 0 + ", " + resMon + ", " + 0 + ")";

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
}
