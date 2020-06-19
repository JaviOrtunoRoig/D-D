package Modelos;

import Vistas.Error;

public class resetError extends Thread {

    Error vista;

    public resetError(Error vista) {
        this.vista = vista;
    }

    public void run() {
        try {
            Thread.sleep(5000);
            vista.resetErrorMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
