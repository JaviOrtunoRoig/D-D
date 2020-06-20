import Controladores.ControladorIdentificarse;
import Vistas.Inicio.VistaDM_Usuario;
import Vistas.Inicio.VistaIniciarSesion;
import Vistas.Inicio.VistaRegistrarse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControladorIdentificarseTest {

    ControladorIdentificarse ci = new ControladorIdentificarse(new VistaRegistrarse(), new VistaIniciarSesion(), new VistaDM_Usuario(null));

    @Test
    void registrarseSuccess() {
        ci.borrarUsuario("admin");
        boolean res = ci.registrarse("admin", "1234", "1234", null);
        assertTrue(res);
    }

    @Test
    void registrarseFail() {
        ci.registrarse("test", "test", "test", null);
        boolean reg2 = ci.registrarse("test", "test", "test", null);
        assertFalse(reg2);
    }

    @Test
    void iniciarSesionSuccess() {
        ci.registrarse("admin", "1234", "1234", null);
        boolean res = ci.iniciarSesion("admin", "1234");
       assertTrue(res);
    }

    @Test
    void borrarUsuarioSuccess() {
        boolean res = ci.borrarUsuario("admin");
        assertTrue(res);
    }
}