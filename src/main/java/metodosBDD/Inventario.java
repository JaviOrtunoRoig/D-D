package metodosBDD;

import java.sql.*;
import java.util.*;


public class Inventario {
    private Connection conn;
    private Statement stmt = null;

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    final String DB_SCHEMA = "dungeonsdragonsdb";

    //  Database credentials
    final String USER = "dundragons";
    final String PASS = "VengerHank";

    private int idPersonaje;
    private int idItem;
    private String tipoItem;
    private int precio;

    public Inventario() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
    }

    public void aniadirItem(String personaje, String tipo, String nombre, int cant) throws SQLException {
        idPersonaje = obtenrIDPersonaje(personaje);


        tipoItem = tipo;

        if (tipoItem.equals("Armaduras")) {
            modificarTP(nombre);
        }


        idItem = obeternIDItem(nombre, tipoItem);

        if (cant == 0) {
            precio = obtenerPrecio(tipo, nombre);
        } else {
            precio = cant;
        }

        if (!permitido(idPersonaje, precio)) {
            System.err.println("No puede permitirse este item");

        } else {
            aniadir();
            quitarDinero(idPersonaje, precio);
            System.out.println("El item ha sido introducido");

           aniadirPeso(idPersonaje, idItem, tipoItem);


        }


    }

    public int obeternIDItem(String nombre, String tipo) throws SQLException {
        String sql = "SELECT nombre, id" + tipo + " FROM " + tipo;
        ResultSet rs = stmt.executeQuery(sql);

        int res = 0;

        while (rs.next()) {

            if (rs.getString("nombre").equals(nombre)) {
                res = rs.getInt("id" + tipo);
            }
        }

        return res;

    }

    public void modificarTP(String nombre) throws SQLException {
        int mod = 0;

        String sqlArmadura = "SELECT nombre, TP FROM Armaduras";
        ResultSet rsArmadura = stmt.executeQuery(sqlArmadura);

        while (rsArmadura.next()) {

            if (rsArmadura.getString("nombre").equals(nombre)) {
                mod = rsArmadura.getInt("TP");
            }
        }

        String sqlTP = "SELECT idPersonaje, TP FROM Personaje";
        ResultSet rsTP = stmt.executeQuery(sqlTP);

        int TP = 0;

        while (rsTP.next()) {

            if (rsTP.getInt("idPersonaje") == idPersonaje) {
                TP = rsTP.getInt("TP");
            }
        }


        String sqlMod = "UPDATE Personaje SET TP = " + (TP - mod) + " WHERE idPersonaje = " + idPersonaje;
        stmt.executeUpdate(sqlMod);
    }

    public int obtenrIDPersonaje(String nom) throws SQLException {
        int res = 0;

        String sql = "SELECT idPersonaje, Nombre FROM Personaje";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            if (rs.getString("Nombre").equals(nom)) {
                res = rs.getInt("idPersonaje");
            }
        }


        return res;
    }

    public void aniadir() throws SQLException {
        String sql = "INSERT INTO Inventario VALUES (" + idPersonaje + ", '" +
                tipoItem + "', " + idItem + ", " + precio + ")";
        stmt.executeUpdate(sql);
    }

    public int obtenerPrecio(String tipo, String nombre) throws SQLException {
        int sol = 0;

        String sqlPrecio = "SELECT nombre, precio FROM " + tipo;
        ResultSet rs = stmt.executeQuery(sqlPrecio);

        while (rs.next()) {
            if (rs.getString("nombre").equals(nombre)) {
                sol = rs.getInt("precio");
            }
        }

        return sol;
    }

    public boolean permitido(int idPer, int cant) throws SQLException {
        String sqlPosible = "SELECT idPersonaje, CantidadOro FROM Moneda";
        ResultSet rsPosible = stmt.executeQuery(sqlPosible);

        boolean posible = false;

        while (rsPosible.next() && !posible) {
            if (idPer == rsPosible.getInt("idPersonaje")) {
                if (cant <= rsPosible.getInt("CantidadOro")) {
                    posible = true;
                }
            }
        }

        return posible;

    }

    public void quitarDinero(int idPer, int cant) throws SQLException {
        String sql = "SELECT idPersonaje, CantidadOro FROM Moneda";
        ResultSet rs = stmt.executeQuery(sql);

        boolean listo = false;
        int mod = 0;

        while (rs.next() && !listo) {
            if (idPer == rs.getInt("idPersonaje")) {
                mod = rs.getInt("CantidadOro");
                mod = mod - cant;

                listo = true;

            }

        }

        String sqlMod = "UPDATE Moneda SET CantidadOro = " + mod + " WHERE idPersonaje = " + idPer;
        stmt.executeUpdate(sqlMod);
    }

    public void aniadirPeso(int idPer, int idIt, String tipo) throws SQLException {
        String sql = "SELECT peso, id" + tipo + " FROM " + tipo;
        ResultSet rs = stmt.executeQuery(sql);

        boolean encontrado = false;
        int peso = 0;
        int pesoAdd = 0;

        while(rs.next() && !encontrado){
            if(rs.getInt("id" + tipo) == idIt){
                encontrado = true;
                pesoAdd = rs.getInt("peso");
            }
        }

        String sqlPer = "SELECT idPersonaje, PesoCalculado FROM Personaje";
        ResultSet rsPer = stmt.executeQuery(sqlPer);

        encontrado = false;

        while(rsPer.next() && !encontrado){
            if(rsPer.getInt("idPersonaje") == idPer){
                encontrado = true;
                peso = rsPer.getInt("PesoCalculado");
            }
        }

        peso = peso + pesoAdd;

        String sqlMod = "UPDATE Personaje SET PesoCalculado = " + peso + " WHERE idPersonaje = " + idPer;
        stmt.executeUpdate(sqlMod);

    }

    public void eliminarItem(int idPer, int idIt) throws SQLException {
        String sql = "DELETE FROM Inventario WHERE idPersonaje = " + idPer + " AND idItem = " + idIt;
        stmt.executeUpdate(sql);
    }

    public int getId(String nom) throws SQLException {
        String sqlUs = "SELECT idPersonaje, usuario FROM Personaje";
        ResultSet rsUs = stmt.executeQuery(sqlUs);

        int idPer = 0;

        while (rsUs.next()) {
            if (nom.equals(rsUs.getString("usuario"))) {
                idPer = rsUs.getInt("idPersonaje");
            }
        }

        return idPer;
    }

    public List<String> mostrarInventario(String nomb) throws SQLException {

        Statement stmt3 = null;
        stmt3 = conn.createStatement();

        String sql = "SELECT idPersonaje, idItem, tipo FROM Inventario";
        ResultSet rs = stmt3.executeQuery(sql);

        Map<String,Set<Integer>> obj = new HashMap<>();

        Set<Integer> ar = new HashSet<>();
        Set<Integer> arm = new HashSet<>();
        Set<Integer> ut = new HashSet<>();

        while(rs.next()){
            if(getId(nomb)== rs.getInt("idPersonaje")){
                if (rs.getString("tipo").equals("Armas")){
                    ar.add(rs.getInt("idItem"));
                } else if(rs.getString("tipo").equals("Armaduras")){
                    arm.add(rs.getInt("idItem"));
                } else if(rs.getString("tipo").equals("Utensilios")){
                    ut.add(rs.getInt("idItem"));
                }
            }
        }

        obj.put("Armas", ar);
        obj.put("Armaduras", arm);
        obj.put("Utensilios", ut);

        List<String> sol = new ArrayList<>();
        boolean encontrado;

        for(String tip : obj.keySet()) {
            Statement stmtAux = null;
            stmtAux = conn.createStatement();

            String sqlInv = "SELECT nombre, id" + tip + " FROM " + tip;
            ResultSet rsInv = stmtAux.executeQuery(sqlInv);

            encontrado = false;

            while(rsInv.next()){
               for(Integer nom : obj.get(tip)){
                   if(nom == rsInv.getInt("id" + tip)){
                       sol.add(rsInv.getString("nombre"));
                       System.out.println(rsInv.getString("nombre"));
                   }
               }
            }
        }

        return sol;
    }





}
