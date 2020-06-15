package Modelos;

public class Jugador {

    private String nombreUsuario;
    private String nombrePersonaje;
    private int vida;

    public Jugador(String Usuario, String Personaje, int vida)
    {
        nombreUsuario = Usuario;
        nombrePersonaje = Personaje;
        this.vida = vida;
    }

    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    public String getNombrePersonaje()
    {
        return nombrePersonaje;
    }

    public int getVida()
    {
        return vida;
    }



}
