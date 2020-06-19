package Controladores;

import Modelos.Principal;
import Vistas.DM.VistaDm;
import Vistas.IniciarDM.VistaConfirCreacionPartida;
import Vistas.IniciarDM.VistaCrearPartida;
import Vistas.IniciarJugador.VistaBuscarPartida;
import Vistas.Inicio.VistaDM_Usuario;
import Vistas.Jugador.VistaJugador;
import metodosBDD.CreacionPersonaje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class ControladorIniciarDM implements ActionListener {



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
    VistaBuscarPartida vistaBuscarPartida;
    VistaJugador vistaJugador;

    public ControladorIniciarDM(String usuario, VistaDM_Usuario vistaDM_usuario) {
        this.usuario = usuario;
        this.vistaDM_usuario = vistaDM_usuario;
    }


    /** Este método buscará al DM en la partida
     *
     * @param nom nombre del usuario.
     * @return id de la partida si es encontrada
     * @return "-1" si el usuario no tiene asociada una partida ó -2" si el usuario tiene asociada una partida, pero no es el DM de la misma
     */
    public int estaDMEnPartida(String nom) throws ClassNotFoundException, SQLException {

        int id = -1;

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
        return id;
    }


    /** Este método creará una partida para el DM
     *
     * @param nombreusuario: nombre del usuario
     * @param contrasena :  nombre del contraseña.
     * @return id de la partida si ha sido creada con éxito
     * @return "-1" si  ha habido algún error creando la partida
     */
    public int crearPartida(String nombreusuario, String contrasena) throws SQLException, ClassNotFoundException {

        int id = -1;

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

                stmt.executeUpdate(insert1);

                String insert2 = "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '" + id + "' WHERE (`nombre` = '" + nombreusuario + "')";

                stmt.executeUpdate(insert2);
            }
        }

        return id;
    }

    /**
     @param nombre: nombre usuario (jugador)
     @return Primer caso: Retornas "-1" si no tiene partida
     Segundo caso: Tiene idPartida, no es DM, devuelves el idPartida
     Tercer caso: Tiene idPartida, es DM, devuelve "-2"
     */

    public int estaJugadorEnPartida (String nombre) throws ClassNotFoundException, SQLException {
        //res es lo que va a devolver este metodo.
        int res=0;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        //vamos a consultar si pertenece a partida por ello hacemos la consulta
        String sqlConsulta = "SELECT partida FROM Usuario WHERE nombre LIKE '" + nombre + "'";
        ResultSet resultado = stmt.executeQuery(sqlConsulta);
        resultado.next();
        //cargamos el int de partida en un int para trabajar con el.
        int result = resultado.getInt("partida");

        //si el codigo de la partida es null en la BD devuelve 0, por ello lo pongo así.
        if (result == 0) {
            res = -1;
        } else {
            //cree otro metodo para saber si dicho jugador, estando en la partida 'x' es dm o no y nos devuelve un bool.
            boolean esDM = EsDM(nombre, result);
            if (esDM) {
                res = -2;
            } else {
                res = result;
            }
        }

        return res;
    }

    /**
     @param nombre: nombre usuario (jugador)
     partida: idPartida.
     @return
     true si el jugador de dicha partida es DM
     false si el jugador de dicha partida no lo es.

     */
    private boolean EsDM (String nombre, int Partida) throws ClassNotFoundException, SQLException {
        boolean esDM=false;

        //creo lo necesario para hacer una consulta preguntando en la partida 'x' cual es el nombre del DM
        //para poder compararo con el nombre del jugador dado.
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        String sqlConsulta = "SELECT DM FROM Partida WHERE idPartida =" + Partida;
        ResultSet resultado = stmt.executeQuery(sqlConsulta);
        resultado.next();
        //cargo el resultado en un string para porder compararlo con comodidad.
        String DM = resultado.getString("DM");
        //por defecto esDM=false. Si el nombre del DM y el nombre del jugador coinciden, esDM pasara a ser true.
        if (DM.equals(nombre)) {
            esDM = true;
        }

        return esDM;
    }


   /* *//**
     *   Unir jugador a partida:
     *     -partida y contraseña coinciden
     *     -unir al jugador
     *
     @param: nombre: nombre usuario (jugador)
     idPartida.
     pass: contrasena partida
     *//*

    private void UnirseAPartida (String nombre, int idpartida, int password) throws ClassNotFoundException, SQLException {

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + "/" + DB_SCHEMA, USER, PASS);
        stmt = conn.createStatement();
        //vamos a ver que la partida existe y coincide con la contrasena.
        String sqlConsultapartida = "SELECT contrasena FROM Partida WHERE idPartida =" + idpartida;
        stmt.executeQuery(sqlConsultapartida);
        ResultSet resultado = stmt.executeQuery(sqlConsultapartida);
        resultado.next();
        int pass = resultado.getInt("contrasena");


        if (pass==password){
            //vamos a realizar la inclusion del usuario a la partida.
            String sqlConsulta = "UPDATE `dungeonsdragonsdb`.`Usuario` SET `partida` = '" + idpartida + "' WHERE (`nombre` = '"+ nombre + "');";
            stmt.executeUpdate(sqlConsulta);

        }else {
            //TODO: Cambiar a error
            System.out.println("Las contrasenas no coinciden");
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaDM_Usuario.DM)) {
            int idPartida = 0;
            try {
                idPartida = estaDMEnPartida(usuario);
            } catch (ClassNotFoundException | SQLException ex) {
                vistaDM_usuario.setMensajeError("Ha ocurrido un error. \n Por favor contacte con nosotros en: \nD&DProyecto@gmail.com");
            }

            if (idPartida >= 0) { //Se ha encontrado partida

                VistaDm vistaDm = new VistaDm();
                ControladorDM controladorDM = new ControladorDM(usuario, vistaDm);

                Principal.frame.setContentPane(vistaDm.getPanel());
                Principal.frame.setVisible(true);

            } else if (idPartida == -1) { //No tiene ninguna partida asociada.
                vistaConfirCreacionPartida = new VistaConfirCreacionPartida();
                vistaConfirCreacionPartida.controlador(this);

                Principal.frame.setContentPane(vistaConfirCreacionPartida.confirmarPartida);
                Principal.frame.setVisible(true);
            } else {
                vistaDM_usuario.setMensajeError("Error: Estás asociado a una partida, \n pero no como dm.");
            }

        } else if (comando.equals(VistaDM_Usuario.JUGADOR)) {

            int id = 0;
            try {
                id = estaJugadorEnPartida(usuario);
            } catch (ClassNotFoundException | SQLException ex) {
                vistaDM_usuario.setMensajeError("Ha ocurrido un error. \n Por favor contacte con nosotros en: \nD&DProyecto@gmail.com");
            }

            if (id >= 0) { //to do bien

                VistaJugador vistaJugador = new VistaJugador();
                try {
                    CreacionPersonaje creacionPersonaje = new CreacionPersonaje();
                    ControladorJugador controladorJugador = new ControladorJugador(vistaJugador, creacionPersonaje.getCaracteristicas(usuario), usuario);
                    vistaJugador.controlador(controladorJugador);

                } catch (SQLException | ClassNotFoundException ex) {
                    vistaDM_usuario.setMensajeError("Error inesperado al recuperar la información del personake de la bdd");
                }
                Principal.frame.setContentPane(vistaJugador.getPanel());
                Principal.frame.setVisible(true);

            } else if (id == -1) { //no tiene partida

                vistaBuscarPartida = new VistaBuscarPartida();
                vistaBuscarPartida.controlador(this);

                ActionListener controladorIniciarJugador = new ControladorIniciarJugador(usuario, vistaBuscarPartida, vistaDM_usuario);
                vistaBuscarPartida.controlador(controladorIniciarJugador);

                Principal.frame.setContentPane(vistaBuscarPartida.BuscarPartida);
                Principal.frame.setVisible(true);

            } else { //tiene partida pero es DM
                vistaDM_usuario.setMensajeError("Tiene partida pero es DM");
            }

        } else if (comando.equals(VistaConfirCreacionPartida.NO)) { //En el aviso de crear nueva partida se dice que no

            Principal.frame.setContentPane(new VistaDM_Usuario(this).DM_Usuario);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaConfirCreacionPartida.SI)) {

            vistaCrearPartida = new VistaCrearPartida();
            vistaCrearPartida.controlador(this);

            Principal.frame.setContentPane(vistaCrearPartida.crear);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaCrearPartida.CREAR)) {

            int estado = 0;
            try {
                estado = crearPartida(usuario, vistaCrearPartida.getPassword());
            } catch (SQLException | ClassNotFoundException ex) {
                vistaCrearPartida.setErrorMessage("Ha ocurrido un error. \n Por favor contacte con nosotros en: \nD&DProyecto@gmail.com");
            }
            if (estado >= 0) {
                vistaCrearPartida.setPasswordLabel("Partida creada");

                VistaDm vistaDm = new VistaDm();

                ControladorDM controladorDM = new ControladorDM(usuario, vistaDm);

                Principal.frame.setContentPane(vistaDm.getPanel());
                Principal.frame.setVisible(true);
            }
            else {
                vistaCrearPartida.setPasswordLabel("Error");
            }

        } else if (comando.equals(VistaCrearPartida.ATRAS)) {

            Principal.frame.setContentPane(vistaConfirCreacionPartida.confirmarPartida);
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaBuscarPartida.ATRAS2)) {
            Principal.frame.setContentPane(new VistaDM_Usuario(this).DM_Usuario);
            Principal.frame.setVisible(true);
        }
    }
}
