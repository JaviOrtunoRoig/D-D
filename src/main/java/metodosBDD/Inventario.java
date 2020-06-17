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

    /**
     *
     * @param personaje
     * @param tipo
     * @param nombre
     * @return null si todo ha ido bien, en otro caso error
     * @throws SQLException
     */
    public String aniadirItem(String personaje, String tipo, String nombre) throws SQLException {
        String error = null;
        idPersonaje = obtenrIDPersonaje(personaje);

        if (tipo.equals("Herrero")){
            tipoItem = "Armas";
        } else if(tipo.equals("Armero")){
            tipoItem = "Armaduras";
        } else if(tipo.equals("Tendero")){
            tipoItem = "Utensilios";
        }


        if (tipoItem.equals("Armaduras")) {
            modificarTP(nombre, getTP(nombre));
        }


        idItem = obeternIDItem(nombre, tipoItem);


        precio = obtenerPrecio(tipoItem, nombre);

        if (!permitido(idPersonaje, precio)) {
            error = "No puede permitirse este item";

        } else {
            aniadir();
            quitarDinero(idPersonaje, precio);

            error = "El item ha sido introducido";

           aniadirPeso(idPersonaje, idItem, tipoItem);
        }

        return error;
    }

    private int obeternIDItem(String nombre, String tipo) throws SQLException {
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

    private int getTP(String nombre) throws SQLException {
        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        int mod = 0;

        String sqlArmadura = "SELECT nombre, TP FROM Armaduras";
        ResultSet rsArmadura = stmt.executeQuery(sqlArmadura);

        while (rsArmadura.next()) {

            if (rsArmadura.getString("nombre").equals(nombre)) {
                mod = rsArmadura.getInt("TP");
            }
        }



        return mod;
    }

    private void modificarTP(String nombre, int mod) throws SQLException {
             Statement stmtAux = null;
             stmtAux = conn.createStatement();

            String sqlTP = "SELECT idPersonaje, TP FROM Personaje";
            ResultSet rsTP = stmtAux.executeQuery(sqlTP);

            int TP = 0;

            while (rsTP.next()) {

                if (rsTP.getInt("idPersonaje") == obtenrIDPersonaje(nombre)) {
                    TP = rsTP.getInt("TP");
                }
            }

            String sqlMod = "UPDATE Personaje SET TP = " + (TP - mod) + " WHERE idPersonaje = " + idPersonaje;
            stmt.executeUpdate(sqlMod);
    }


    private int obtenrIDPersonaje(String nom) throws SQLException {
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

    private void aniadir() throws SQLException {
        String sql = "INSERT INTO Inventario VALUES (" + idPersonaje + ", '" +
                tipoItem + "', " + idItem + ", " + precio + ")";
        stmt.executeUpdate(sql);
    }

    private int obtenerPrecio(String tipo, String nombre) throws SQLException {
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

    private boolean permitido(int idPer, int cant) throws SQLException {
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

    private void quitarDinero(int idPer, int cant) throws SQLException {
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

    private void aniadirPeso(int idPer, int idIt, String tipo) throws SQLException {
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

    private boolean posible(String nombrePersonaje, String nombreObjeto, String tipo) throws SQLException {
        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        String sql = "SELECT idPersonaje, idItem, tipo FROM Inventario";
        ResultSet rs = stmtAux.executeQuery(sql);

        boolean sol = false;

        while(rs.next() && !sol) {
            if (rs.getInt("idPersonaje") == obtenrIDPersonaje(nombrePersonaje) &&
               rs.getInt("idItem") == obeternIDItem(nombreObjeto,tipo) &&
               tipo.equals(rs.getString("tipo"))){

                sol = true;

            }
        }

        return sol;
    }

    /**
     *
     * @param nombrePersonaje
     * @param nombreObjeto
     * @param tipo
     * @return null si todo ha ido bien
     * @throws SQLException
     */
    public String eliminarItem(String nombrePersonaje, String nombreObjeto, String tipo) throws SQLException {
        String error = null;
        Statement stmtAux = null;
        stmtAux = conn.createStatement();

        int idPer = obtenrIDPersonaje(nombrePersonaje);
        int idIt = obeternIDItem(nombreObjeto, tipo);

        if(tipo.equals("Armaduras")){
            modificarTP(nombrePersonaje, getTP(nombreObjeto) * (-1));
        }

        if (posible(nombrePersonaje, nombreObjeto, tipo)) {
            String sql = "DELETE FROM Inventario WHERE idPersonaje = " + idPer + " AND idItem = " + idIt;
            stmtAux.executeUpdate(sql);
        } else {
            error = "No tiene este Item en su inventario";
        }

        return error;
    }

    private int getId(String nom) throws SQLException {
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

        for(String tip : obj.keySet()) {
            Statement stmtAux = null;
            stmtAux = conn.createStatement();

            String sqlInv = "SELECT peso, nombre, id" + tip + " FROM " + tip;
            ResultSet rsInv = stmtAux.executeQuery(sqlInv);

            sol.add(tip + ": \n");

            while(rsInv.next()){
               for(Integer nom : obj.get(tip)){
                   if(nom == rsInv.getInt("id" + tip)){
                       sol.add("Nombre: " + rsInv.getString("nombre") +
                               "| Peso: " +Integer.toString(rsInv.getInt("peso")));

                   }
               }
            }
        }

        return sol;
    }
}
