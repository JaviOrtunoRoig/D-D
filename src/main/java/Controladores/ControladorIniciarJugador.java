package Controladores;

import Modelos.Principal;
import Vistas.VistaBuscarPartida;
import Vistas.VistaDM_Usuario;
import Vistas.VistaPersonajeAuto_Manual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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

    VistaBuscarPartida vistaBuscarPartida;

    VistaPersonajeAuto_Manual vistaPersonajeAuto_manual;


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
     */

    public void UnirseAPartida (String nombre, int idpartida, int password) {
        System.out.println("ESTO DEBERIA APARECER SOLO UNA VEZ");

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
            stmt = conn.createStatement();
            //vamos a ver que la partida existe y coincide con la contrasena.
            String sqlConsultapartida = "SELECT contrasena FROM Partida WHERE idPartida =" + idpartida;
            stmt.executeQuery(sqlConsultapartida);
            ResultSet resultado = stmt.executeQuery(sqlConsultapartida);
            resultado.next();
            int pass = resultado.getInt("contrasena");


            System.out.println("Voy a comprobar si las contrasenas coinciden. He obtenido que de la partida: "
                    + idpartida + " la pass= " + pass + ". La password dada ha sido: " + password);
            if (pass==password){
                //vamos a realizar la inclusion del usuario a la partida.
                //UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '1234' WHERE (`nombre` = 'micho');
                System.out.println("Las contrasenas coinciden, lo anado.");
                System.out.println("---------------Sentencia:");
                System.out.println("UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '" + idpartida + "' WHERE (`nombre` = '"+ nombre + "');");
                String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '" + idpartida + "' WHERE (`nombre` = '"+ nombre + "');";
                stmt.executeUpdate(sqlConsulta);
                System.out.println("Sentencia ejecutada.");

            } else{
                System.out.println("Las contrasenas no coinciden");
            }

            System.out.println("El if se ha hecho y ya debería estar unido. No errors me vuelvo al main");

        } catch (SQLException e){
            System.err.println("Error de conexion a la base de datos");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaBuscarPartida.UNIRMEPARTIDA)) {
            this.UnirseAPartida(usuario, Integer.parseInt(vistaBuscarPartida.getIdPartida()), Integer.parseInt(vistaBuscarPartida.getPasswordField()));

            vistaPersonajeAuto_manual = new VistaPersonajeAuto_Manual();
            Principal.frame.setContentPane(vistaPersonajeAuto_manual.pantalla);
            Principal.frame.setVisible(true);
        }
    }
}
