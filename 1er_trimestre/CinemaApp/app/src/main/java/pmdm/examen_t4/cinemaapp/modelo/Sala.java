package pmdm.examen_t4.cinemaapp.modelo;

public class Sala {

    private String numero;
    private String hora;
    private String asientosLibres;
    private String numeroFilas;
    private String asientosReservados;
    private String titulo;

    // Constructores
    public Sala() {
    }

    public Sala(String numero, String hora, String asientosLibres, String numeroFilas, String asientosReservados, String titulo) {
        this.numero = numero;
        this.hora = hora;
        this.asientosLibres = asientosLibres;
        this.numeroFilas = numeroFilas;
        this.asientosReservados = asientosReservados;
        this.titulo = titulo;
    }

    // Getters y Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAsientosLibres() {
        return asientosLibres;
    }

    public void setAsientosLibres(String asientosLibres) {
        this.asientosLibres = asientosLibres;
    }

    public String getNumeroFilas() {
        return numeroFilas;
    }

    public void setNumeroFilas(String numeroFilas) {
        this.numeroFilas = numeroFilas;
    }

    public String getAsientosReservados() {
        return asientosReservados;
    }

    public void setAsientosReservados(String asientosReservados) {
        this.asientosReservados = asientosReservados;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
