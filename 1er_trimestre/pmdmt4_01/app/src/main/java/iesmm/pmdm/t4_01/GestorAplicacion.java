package iesmm.pmdm.t4_01;

public interface GestorAplicacion {
    public void cargarNavegadorWeb(String url);
    public void abrirMarcadorLlamada();
    public void marcarLlamada(String telefono);
    public void realizarLlamada(String telefono);
    public void mandarMensaje(String contenido);
    public void mandarMensaje(String telefono, String contenido);
}