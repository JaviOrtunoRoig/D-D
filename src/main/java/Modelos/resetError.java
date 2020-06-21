package Modelos;

import Vistas.Error;

import java.util.Date;

public class resetError extends Thread {

    Error vista;

    public resetError(Error vista) {
        this.vista = vista;
    }

    public void run() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vista.resetErrorMessage();
    }
}
