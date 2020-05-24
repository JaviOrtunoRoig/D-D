import Controladores.ControladorIdentificarse;
import Vistas.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControladorIdentificarseTest {

    ControladorIdentificarse ci = new ControladorIdentificarse(new VistaRegistrarse(), new VistaIniciarSesion(), new VistaDM_Usuario(null));

    @Test
    void registrarseSuccess() {
        ci.borrarUsuario("admin");
        boolean res = ci.registrarse("admin", "1234", "1234");
        assertTrue(res);
    }

    @Test
    void registrarseFail() {
        ci.registrarse("test", "test", "test");
        boolean reg2 = ci.registrarse("test", "test", "test");
        assertFalse(reg2);
    }

    @Test
    void iniciarSesionSuccess() {
        ci.registrarse("admin", "1234", "1234");
        boolean res = ci.iniciarSesion("admin", "1234");
       assertTrue(res);
    }

    @Test
    void borrarUsuarioSuccess() {
        boolean res = ci.borrarUsuario("admin");
        assertTrue(res);
    }
}