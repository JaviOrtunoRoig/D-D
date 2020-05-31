package Controladores;

import Modelos.Stats;
import metodosBDD.QueriesPersonaje;

public class ControladorStats {

    //todo:obtener los fields donde voy a actualizar los datos
    //todo:obtener id del personaje que el usuario esta visualizando

    private void llamarBaseDeDatos() {
        QueriesPersonaje qp = new QueriesPersonaje();
        Stats stats;
        String id = "idPersonaje";
    while(true) {
        try {
            Thread.sleep(3000);
            stats = qp.obtenerStats(id);
            //todo: actualizar los fields con la informacion que está en stats.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    }
}
