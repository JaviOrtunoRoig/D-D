package metodosBDD;

public class Armadura implements Objeto {

    private String nombre;
    private int peso, precio, TP;

    public Armadura(String nombre, int peso, int precio, int TP)
    {
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
        this.TP = TP;

    }

    public String getNombre() {
        return nombre;
    }


    public int getPeso() {
        return peso;
    }


    public int getPrecio() {
        return precio;
    }

    public int getTP() {
        return TP;
    }


}
