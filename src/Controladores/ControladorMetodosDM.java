package Controladores;

import Modelos.Principal;
import Vistas.VistaConfirCreacionPartida;
import Vistas.VistaDM_Usuario;
import Vistas.VistaPG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ControladorMetodosDM implements ActionListener {



    private Connection conn = null;
    private Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";

    private VistaDM_Usuario vistaDM_usuario;

    private String usuario;

    public ControladorMetodosDM(String usuario, VistaDM_Usuario vistaDM_usuario) {
        this.usuario = usuario;
        this.vistaDM_usuario = vistaDM_usuario;
    }


    /** Este método buscará al DM en la partida
     *
     * @param nom nombre del usuario.
     * @return id de la partida si es encontrada o "-1" si no ha sido encontrada
     */
    public int estaDMEnPartida(String nom) {

        int id = -1;

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

            stmt = conn.createStatement();
            boolean encontrado = false;

            String query1 = "SELECT nombre, partida FROM Usuario";
            ResultSet rsConsulta = stmt.executeQuery(query1);

            while (rsConsulta.next() && !encontrado){

                String nombreBD = rsConsulta.getString("nombre");

                if (nombreBD.equals(nom)) {
                    id = rsConsulta.getInt("partida");
                    encontrado = true;
                }
            }

            if (encontrado) {
                String query2 = "SELECT idPartida, DM FROM Partida";
                ResultSet rsConsulta2 = stmt.executeQuery(query2);
                boolean encontradoDM = false;

                while (rsConsulta2.next() && !encontradoDM) {

                    int idPartida = rsConsulta2.getInt("idPartida");
                    String DM = rsConsulta2.getString("DM");

                    if (idPartida == id && nom.equals(DM)) {
                        encontradoDM = true;
                    }
                }

                if (!encontradoDM) {
                    id = -1;
                }
            }

        } catch(SQLException e) {
            System.err.println("Error en la conexion a la base de datos");
        } catch(Exception e) {
            System.err.println("Error : " + e.getMessage());
        }

        return id;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaDM_Usuario.DM)) {
            int idPartida = estaDMEnPartida(usuario);

            if (idPartida >= 0) { //Se ha encontrado partida
                //TODO: Redirigir a la partida.
            } else { //No tiene ninguna partida asociada.
                VistaConfirCreacionPartida vistaConfirCreacionPartida = new VistaConfirCreacionPartida();
                vistaConfirCreacionPartida.controlador(this);

                Principal.frame.setContentPane(vistaConfirCreacionPartida.confirmarPartida);
                Principal.frame.setVisible(true);
            }

        } else if (comando.equals(VistaDM_Usuario.JUGADOR)) {
            //TODO: Que hacer si elige ser jugador
        } else if (comando.equals(VistaConfirCreacionPartida.NO)) { //En el aviso de crear nueva partida se dice que no

            Principal.frame.setContentPane(new VistaDM_Usuario(this).DM_Usuario);
            Principal.frame.setVisible(true);
        }
    }
}
