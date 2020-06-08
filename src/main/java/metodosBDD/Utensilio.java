package metodosBDD;

public class Utensilio implements Objeto {

    private String nombre;
    private int peso;
    private int precio;

    public Utensilio(String nombre, int peso, int precio)
    {
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
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
}
