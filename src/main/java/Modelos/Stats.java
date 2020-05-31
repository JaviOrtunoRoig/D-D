package Modelos;

public class Stats {

    private int vida;
    private int experiencia;
    private int oro;

    public Stats(int vida, int experiencia, int oro) {
        this.vida = vida;
        this.experiencia = experiencia;
        this.oro = oro;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }
}
