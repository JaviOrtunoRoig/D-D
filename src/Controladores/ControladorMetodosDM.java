package Controladores;

import Modelos.Principal;
import Vistas.VistaConfirCreacionPartida;
import Vistas.VistaCrearPartida;
import Vistas.VistaDM_Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class ControladorMetodosDM implements ActionListener {



    private Connection conn = null;
    private Statement stmt = null;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://database-iis.cobadwnzalab.eu-central-1.rds.amazonaws.com";
    static final String DB_SCHEMA = "dungeonsdragonsdb";

    //Database credentials
    static final String USER = "dundragons";
    static final String PASS = "VengerHank";

    private String usuario;

    VistaDM_Usuario vistaDM_usuario;
    VistaCrearPartida vistaCrearPartida;
    VistaConfirCreacionPartida vistaConfirCreacionPartida;

    public ControladorMetodosDM(String usuario, VistaDM_Usuario vistaDM_usuario) {
        this.usuario = usuario;
        this.vistaDM_usuario = vistaDM_usuario;
    }


    /** Este método buscará al DM en la partida
     *
     * @param nom nombre del usuario.
     * @return id de la partida si es encontrada
     * @return "-1" si el usuario no tiene asociada una partida ó -2" si el usuario tiene asociada una partida, pero no es el DM de la misma
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
            if(id == 0)
            {
                id = -1;
            }
            else if (encontrado) {
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
                    id = -2;
                }
            }

        } catch(
                SQLException e) {
            System.err.println("Error en la conexion a la base de datos");
        } catch(Exception e) {
            System.err.println("Error : " + e.getMessage());
        }

        return id;
    }


    /** Este método creará una partida para el DM
     *
     * @param nombreusuario: nombre del usuario
     * @param contrasena :  nombre del contraseña.
     * @return id de la partida si ha sido creada con éxito
     * @return "-1" si  ha habido algún error creando la partida
     */
    public int crearPartida(String nombreusuario, String contrasena) {

        int id = -1;

        try {

            Calendar fecha = new GregorianCalendar();
            int anio = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH);
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            String fechasistema = anio + "-" + (mes+1) + "-" + dia;

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);

            stmt = conn.createStatement();

            Random r = new Random();


            boolean seguir = true;

            while(seguir) {

                int idPartida = r.nextInt(10000);

                String query1 = "SELECT idPartida FROM Partida";
                ResultSet rsConsulta1 = stmt.executeQuery(query1);
                boolean encontrado = false;

                while (rsConsulta1.next() && !encontrado) {

                    int idPartidaBBDD = rsConsulta1.getInt("idPartida");


                    if (idPartidaBBDD == idPartida) {
                        encontrado = true;
                    }
                }

                if(!encontrado) {
                    seguir = false;
                    id = idPartida;
                    String insert1 = "INSERT INTO `dungeonsdragonsdb`.`Partida` (`idPartida`, `contrasena`, `fechaCreacion`, `numeroJugadores`, `DM`) " +
                            "VALUES ('" + id + "','" + contrasena + "','" + fechasistema + "','" + 0 + "','" + nombreusuario + "')";

                    System.out.println(insert1);

                    stmt.executeUpdate(insert1);

                    String insert2 = "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '" + id + "' WHERE (`nombre` = '" + nombreusuario + "')";

                    stmt.executeUpdate(insert2);
                }
            }




        }catch(SQLException e) {
            System.err.println("Error en la base de Datos");
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return id;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaDM_Usuario.DM)) {
            int idPartida = estaDMEnPartida(usuario);

            System.out.println(idPartida);

            if (idPartida >= 0) { //Se ha encontrado partida
                //TODO: Redirigir a la partida.
            } else if (idPartida == -1) { //No tiene ninguna partida asociada.
                vistaConfirCreacionPartida = new VistaConfirCreacionPartida();
                vistaConfirCreacionPartida.controlador(this);

                Principal.frame.setContentPane(vistaConfirCreacionPartida.confirmarPartida);
                Principal.frame.setVisible(true);
            } else {
                vistaDM_usuario.setMensajeError("Error: Estás asociado a una partida, \n pero no como dm.");
            }

        } else if (comando.equals(VistaDM_Usuario.JUGADOR)) {
            //TODO: Que hacer si elige ser jugador
        } else if (comando.equals(VistaConfirCreacionPartida.NO)) { //En el aviso de crear nueva partida se dice que no

            Principal.frame.setContentPane(new VistaDM_Usuario(this).DM_Usuario);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaConfirCreacionPartida.SI)) {

            vistaCrearPartida = new VistaCrearPartida();
            vistaCrearPartida.controlador(this);

            Principal.frame.setContentPane(vistaCrearPartida.crear);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaCrearPartida.CREAR)) {
            int estado = crearPartida(usuario, vistaCrearPartida.getPassword());
            if (estado >= 0) {
                vistaCrearPartida.setPasswordLabel("Partida creada");
            }
            else {
                vistaCrearPartida.setPasswordLabel("Error");
            }
        } else if (comando.equals(VistaCrearPartida.ATRAS)) {
            Principal.frame.setContentPane(vistaConfirCreacionPartida.confirmarPartida);
            Principal.frame.setVisible(true);
        }
    }
}
