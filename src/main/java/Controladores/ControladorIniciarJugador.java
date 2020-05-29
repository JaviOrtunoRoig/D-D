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
     @return:
     -1:si to do ha ido bien
     -2:si no se ha encontrado la partida con ese id (SQLException en general)
     -3: Exception pero no SQLException. Otro tipo de error.
     -4:no era la contraseña de la partida
     */

    public int UnirseAPartida (String nombre, int idpartida, String password) {
        int res;

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
                //todo: crear metodo contrasenas iguales error.
                res=4;

            }

            return res;
        } catch (SQLException e){
            //System.err.println("Error de conexion a la base de datos");
            //todo: este error salta cuando la partida no existe, crear este mensaje de error.
            res=2;
            return res;
        } catch (Exception e){
            res=3;
            return res;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaBuscarPartida.UNIRMEPARTIDA)) {
            this.UnirseAPartida(usuario, Integer.parseInt(vistaBuscarPartida.getIdPartida()),vistaBuscarPartida.getPasswordField());

            vistaPersonajeAuto_manual = new VistaPersonajeAuto_Manual();
            Principal.frame.setContentPane(vistaPersonajeAuto_manual.pantalla);
            Principal.frame.setVisible(true);
        }
    }
}
