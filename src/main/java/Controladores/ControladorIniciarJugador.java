package Controladores;

import Modelos.Principal;
import Vistas.IniciarJugador.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ControladorIniciarJugador implements ActionListener {

    private Connection conn = null;
    private Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";


    String usuario;
    boolean manual;

    VistaBuscarPartida vistaBuscarPartida;
    VistaPersonajeAuto_Manual vistaPersonajeAuto_manual;
    VistaEleccionManual vistaEleccionManual;
    VistaMostrarEstadisticas vistaMostrarEstadisticas;
    VistaElegirRaza vistaElegirRaza;


    int[] caracteristicas;
    Map<String,Integer> caracteristicasMapa;
    boolean[] razasDisponibles;

    public ControladorIniciarJugador(String usuario, VistaBuscarPartida vistaBuscarPartida) {
        this.usuario = usuario;
        this.vistaBuscarPartida = vistaBuscarPartida;
    }

    /**
     *   Unir jugador a partida:
     *     -partida y contraseña coinciden
     *     -unir al jugador
     *
     @param nombre: nombre usuario (jugador)
     idPartida.
     pass: contrasena partida
     @return
     -1:si to do ha ido bien
     -2:si no se ha encontrado la partida con ese id (SQLException en general)
     -3: Exception pero no SQLException. Otro tipo de error.
     -4:no era la contraseña de la partida
     */

    public int UnirseAPartida (String nombre, int idpartida, String password) {
        int res;

        //TODO: quitar esto, lo pongo ahora para no tener que crear cuentas todo el rato
        if (nombre.equals("d")) {
            res = 1;
            return res;
        }

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();
            //vamos a ver que la partida existe y coincide con la contrasena.
            String sqlConsultapartida = "SELECT contrasena FROM Partida WHERE idPartida =" + idpartida;
            stmt.executeQuery(sqlConsultapartida);
            ResultSet resultado = stmt.executeQuery(sqlConsultapartida);
            resultado.next();
            String pass = resultado.getString("contrasena");


            if (pass.equals(password)){
                //vamos a realizar la inclusion del usuario a la partida.
                //UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '1234' WHERE (`nombre` = 'micho');
                String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '" + idpartida + "' WHERE (`nombre` = '"+ nombre + "');";
                stmt.executeUpdate(sqlConsulta);
                res=1;


            }else{
                vistaBuscarPartida.setMensajeError("Las password no coinciden");
                res=4;
            }

            return res;
        } catch (SQLException e) {
            vistaBuscarPartida.setMensajeError("La partida no existe");
            res=2;
            return res;
        } catch (Exception e) {
            vistaBuscarPartida.setMensajeError("Ha ocurrido un error inesperado");
            res=3;
            return res;
        }
    }

    /**
     * Asiga automaticamnete las caracteristicas.
     * @return array con las caracteristicas de los personajes
     */
    public int[] asignacionCaracteristicasAutomatica() {

        int[] caracteristicasValores = new int[6];

        for (int i = 0; i < 6; i++) {
            int num = 0;

            num = (int) (Math.random() * 16 + 3);

            caracteristicasValores[i] = num;
        }
        return caracteristicasValores;
    }

    public Map<String, Integer> asignacionCaracteristicas(int [] num, Map<String, Integer> caracteristicas){
        caracteristicas.put("Fuerza", num[0]);
        caracteristicas.put("Inteligencia", num[1]);
        caracteristicas.put("Sabiduria", num[2]);
        caracteristicas.put("Destreza", num[3]);
        caracteristicas.put("Constitucion", num[4]);
        caracteristicas.put("Carisma", num[5]);

        return caracteristicas;
    }

    public boolean [] razasDisponibles (Map<String, Integer> caracteristicas) {

        boolean [] disponible = new boolean [7];
        try {

            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();


            int cont = 0;

            while(cont < 7) {
                String contMasUno = String.valueOf(cont+1);
                String sql = "SELECT idRazas, CaracterísticaPrincipal FROM Razas WHERE idRazas = " + contMasUno;
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();

                if (caracteristicas.get(rs.getString("CaracterísticaPrincipal")) >= 9 && rs.getInt("idRazas") == cont + 1) {
                    disponible[cont] = true;
                } else {
                    disponible[cont] = false;
                }
                cont++;
            }
        } catch (SQLException e) {
            //TODO: Mostrar error en la GUI
            System.out.println("erorr BDD");
        }
        return disponible;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaBuscarPartida.UNIRMEPARTIDA)) {

            int estado = this.UnirseAPartida(usuario, Integer.parseInt(vistaBuscarPartida.getIdPartida()),vistaBuscarPartida.getPasswordField());

            if (estado == 1) {
                vistaPersonajeAuto_manual = new VistaPersonajeAuto_Manual();
                vistaPersonajeAuto_manual.controlador(this);
                Principal.frame.setContentPane(vistaPersonajeAuto_manual.pantalla);
                Principal.frame.setVisible(true);
            } else if (estado == 2) {
                vistaBuscarPartida.setMensajeError("No existe una partida con ese id");
            } else if (estado == 3 ) {
                vistaBuscarPartida.setMensajeError("Algo inesperado a ocurrido");
            } else {
                vistaBuscarPartida.setMensajeError("Contraseña erronea");
            }

        } else if (comando.equals(VistaPersonajeAuto_Manual.MANUAL)) {

            manual = true;

            vistaEleccionManual = new VistaEleccionManual();
            vistaEleccionManual.controlador(this);;

            Principal.frame.setContentPane(vistaEleccionManual.getPanel());
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaPersonajeAuto_Manual.AUTOMATICO)) {

            manual = false;

            caracteristicas = asignacionCaracteristicasAutomatica();

            vistaMostrarEstadisticas = new VistaMostrarEstadisticas(caracteristicas[0], caracteristicas[1], caracteristicas[2], caracteristicas[3],
                    caracteristicas[4], caracteristicas[5]);
            vistaMostrarEstadisticas.controlador(this);

            caracteristicasMapa = new TreeMap<>();
            caracteristicasMapa = asignacionCaracteristicas(caracteristicas, caracteristicasMapa);
            razasDisponibles = razasDisponibles(caracteristicasMapa);

            Principal.frame.setContentPane(vistaMostrarEstadisticas.getPanel());
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaMostrarEstadisticas.ATRAS)) {

            Principal.frame.setContentPane(vistaPersonajeAuto_manual.pantalla);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaMostrarEstadisticas.ELEGIRRAZA)) {

            vistaElegirRaza = new VistaElegirRaza();
            vistaElegirRaza.visualizarBotones(razasDisponibles);
            vistaElegirRaza.controlador(this);

            Principal.frame.setContentPane(vistaElegirRaza.getPanel());
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaEleccionManual.ESTADISTICAS)) {

            try {
                caracteristicas = vistaEleccionManual.getCaracteristicas();

                vistaMostrarEstadisticas = new VistaMostrarEstadisticas(caracteristicas[0], caracteristicas[1], caracteristicas[2], caracteristicas[3],
                        caracteristicas[4], caracteristicas[5]);

                caracteristicasMapa = new HashMap<>();
                caracteristicasMapa = asignacionCaracteristicas(caracteristicas, caracteristicasMapa);
                razasDisponibles = razasDisponibles(caracteristicasMapa);

                vistaMostrarEstadisticas = new VistaMostrarEstadisticas(caracteristicas[0], caracteristicas[1], caracteristicas[2], caracteristicas[3],
                        caracteristicas[4], caracteristicas[5]);
                vistaMostrarEstadisticas.controlador(this);

                Principal.frame.setContentPane(vistaMostrarEstadisticas.getPanel());
                Principal.frame.setVisible(true);
            } catch (NumberFormatException a) {
                vistaEleccionManual.setErrorMessage("Las estadisticas tienen que ser numeros.");
            }

        } else if (comando.equals(VistaEleccionManual.ATRAS2)) {

            Principal.frame.setContentPane(vistaPersonajeAuto_manual.pantalla);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaElegirRaza.ATRAS4)) {

            Principal.frame.setContentPane(vistaMostrarEstadisticas.getPanel());
            Principal.frame.setVisible(true);

        }

    }
}
