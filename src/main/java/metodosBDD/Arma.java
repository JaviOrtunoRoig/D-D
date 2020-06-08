package metodosBDD;

public class Arma implements Objeto {

    private String nombre;
    private int peso;
    private int precio;
    private int tipoDado;



    public Arma(String nombre, int peso, int precio, int tipoDado)
    {
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
        this.tipoDado = tipoDado;
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

    public int getTipoDado() {
        return tipoDado;
    }
}
