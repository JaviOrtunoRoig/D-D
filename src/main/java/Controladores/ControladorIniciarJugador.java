package Controladores;

import Modelos.Principal;
import Vistas.IniciarJugador.*;
import Vistas.Inicio.VistaDM_Usuario;
import Vistas.Jugador.VistaJugador;
import metodosBDD.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
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
    int raza = 0;
    int idPartida = 0;

    VistaDM_Usuario vistaDM_usuario;
    VistaBuscarPartida vistaBuscarPartida;
    VistaPersonajeAuto_Manual vistaPersonajeAuto_manual;
    VistaEleccionManual vistaEleccionManual;
    VistaMostrarEstadisticas vistaMostrarEstadisticas;
    VistaElegirRaza vistaElegirRaza;
    VistaFinAutomatico vistaFinAutomatico;
    VistaFinManual vistaFinManual;

    CreacionPersonaje creacionPersonajeMetodos;


    int[] caracteristicas;
    Map<String,Integer> caracteristicasMapa;
    boolean[] razasDisponibles;

    public ControladorIniciarJugador(String usuario, VistaBuscarPartida vistaBuscarPartida, VistaDM_Usuario vistaDM_usuario) {
        this.usuario = usuario;
        this.vistaBuscarPartida = vistaBuscarPartida;
        this.vistaDM_usuario = vistaDM_usuario;
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

        try {
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

                this.idPartida = idpartida;

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

    public void newBranch() {
        if (manual) {
            vistaFinManual = new VistaFinManual();
            vistaFinManual.controlador(this);
            Principal.frame.setContentPane(vistaFinManual.getPanel());
        } else {
            vistaFinAutomatico = new VistaFinAutomatico();
            vistaFinAutomatico.controlador(this);
            Principal.frame.setContentPane(vistaFinAutomatico.getPanel());
        }
        Principal.frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(VistaBuscarPartida.UNIRMEPARTIDA)) {

            int estado = this.UnirseAPartida(usuario, Integer.parseInt(vistaBuscarPartida.getIdPartida()),vistaBuscarPartida.getPasswordField());

            vistaPersonajeAuto_manual = new VistaPersonajeAuto_Manual();
            vistaPersonajeAuto_manual.controlador(this);
            if (estado == 1) {
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

            if (manual) {
                Principal.frame.setContentPane(vistaEleccionManual.getPanel());
                Principal.frame.setVisible(true);
            } else {
                Principal.frame.setContentPane(vistaPersonajeAuto_manual.pantalla);
                Principal.frame.setVisible(true);
            }

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

        } else if (comando.equals(VistaElegirRaza.GUERRERO)) {

            raza = 1;
            newBranch();

        } else if (comando.equals(VistaElegirRaza.ELFO)) {

            raza = 2;
            newBranch();

        } else if (comando.equals(VistaElegirRaza.ENANO)) {

            raza = 3;
            newBranch();

        } else if (comando.equals(VistaElegirRaza.LADRON)) {
            raza = 4;
            newBranch();

        } else if (comando.equals(VistaElegirRaza.HOBBIT)) {
            raza = 5;
            newBranch();

        } else if (comando.equals(VistaElegirRaza.CLERIGO)) {
            raza = 6;
            newBranch();

        } else if (comando.equals(VistaElegirRaza.MAGO)) {
            raza = 7;
            newBranch();

        } else if (comando.equals(VistaFinAutomatico.ATRAS3)) {

            Principal.frame.setContentPane(vistaElegirRaza.getPanel());
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaFinAutomatico.ENTRARPARTIDAAUTOMATICO)) {

            try {
                String nombre = vistaFinAutomatico.getNombrePersonaje();
                String rasgos = vistaFinAutomatico.getRasgosPersonaje();

                if (nombre.equals("") || rasgos.equals("")) {
                    vistaFinAutomatico.setMensajeError("Hay datos sin introducir");
                } else if (rasgos.length() > 100) {
                    vistaFinAutomatico.setMensajeError("Longitud maxima superada");
                } else {
                    creacionPersonajeMetodos = new CreacionPersonaje(vistaFinAutomatico.getNombrePersonaje(),
                            vistaFinAutomatico.getRasgosPersonaje(), caracteristicas, raza, usuario, caracteristicasMapa, idPartida);

                    VistaJugador vistaJugador = new VistaJugador();
                    ControladorJugador controladorJugador = new ControladorJugador(vistaJugador, caracteristicas, usuario);
                    vistaJugador.controlador(controladorJugador);

                    Principal.frame.setContentPane(vistaJugador.getPanel());
                    Principal.frame.setVisible(true);

                }
            } catch (ClassNotFoundException | SQLException ex) {
                vistaFinAutomatico.setMensajeError("Error inesperado");
            }

        } else if (comando.equals(VistaFinManual.ATRAS4)) {

            Principal.frame.setContentPane(vistaMostrarEstadisticas.getPanel());
            Principal.frame.setVisible(true);

        } else if (comando.equals(VistaFinManual.ENTRARPARTIDAMANUAL)) {
            String nombre = vistaFinManual.getNombre();
            int comportamientoIndex = vistaFinManual.getComportamientoIndex();
            String comportamiento;
            String idiomas = vistaFinManual.getIdioma();
            String moneda = vistaFinManual.getMoneda();
            String rasgos = vistaFinManual.getRasgo();
            String vida = vistaFinManual.getVidaDado();

            System.out.println(caracteristicasMapa);

            if (nombre.equals("") || idiomas.equals("") || moneda.equals("") || rasgos.equals("") || vida.equals("")) {
                vistaFinManual.setErrorMessage("Longitud maxima superada");
            } else {
                if (comportamientoIndex == 0) {
                    comportamiento = "Legal";
                } else if (comportamientoIndex == 1) {
                    comportamiento = "Neutral";
                } else {
                    comportamiento = "Caotico";
                }
                try {
                    creacionPersonajeMetodos = new CreacionPersonaje(caracteristicas, nombre, rasgos, idiomas, Integer.parseInt(vida),
                            comportamiento, Integer.parseInt(moneda), usuario, idPartida, caracteristicasMapa, raza);


                    VistaJugador vistaJugador = new VistaJugador();
                    ControladorJugador controladorJugador = new ControladorJugador(vistaJugador, caracteristicas, usuario);
                    vistaJugador.controlador(controladorJugador);

                    Principal.frame.setContentPane(vistaJugador.getPanel());
                    Principal.frame.setVisible(true);

                } catch (NumberFormatException ex) {
                    vistaFinManual.setErrorMessage("No ha introducido un formato valido para la vida o las monedas");
                } catch (SQLException | ClassNotFoundException ex) {
                    vistaFinManual.setErrorMessage("Error inesperado");
                }
            }
        }
    }
}
