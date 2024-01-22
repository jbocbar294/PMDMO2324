package iesmm.pmdm.pmdm_t4_users;

public class Usuario {
    private String usuario, contrasenya, email, poblacion;
    private int telefono;

    public Usuario(String usuario, String contrasenya, String email, int telefono, String poblacion) {
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.email = email;
        this.poblacion = poblacion;
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
